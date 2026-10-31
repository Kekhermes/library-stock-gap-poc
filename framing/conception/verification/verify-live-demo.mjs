import assert from 'node:assert/strict';
import fs from 'node:fs';

const base = process.env.STOCKGAP_API_URL || 'http://127.0.0.1:3000/api';
const reset = process.argv.includes('--reset');
const restore = process.argv.includes('--restore');
const allOrders = process.argv.includes('--all-orders');
const output = process.argv.find(argument => argument.startsWith('--report='))?.slice(9);
const report = { startedAt: new Date().toISOString(), realOpenAI: true, steps: [], events: [] };
const abort = new AbortController();
const timeout = setTimeout(() => abort.abort(), 15 * 60 * 1000);
let cursor;
let watching = true;
let streamFailure;
const listeners = new Set();

async function request(path, method = 'GET', body) {
  const response = await fetch(base + path, { method, headers: body === undefined ? {} : { 'Content-Type': 'application/json' },
    body: body === undefined ? undefined : JSON.stringify(body), signal: AbortSignal.any([abort.signal, AbortSignal.timeout(30000)]) });
  const data = await response.json();
  if (!response.ok) throw new Error(`${path}: HTTP ${response.status} ${data.error || 'commande refusée'}`);
  return data;
}

function step(name, data) {
  report.steps.push({ name, at: new Date().toISOString(), ...data });
  console.log(JSON.stringify({ step: name, ...data }));
}

async function observe() {
  let reconnects = 0;
  while (watching && !abort.signal.aborted) {
    const response = await fetch(base + '/events?after=' + encodeURIComponent(cursor), { signal: abort.signal });
    if (!response.ok) throw new Error('Flux de résultats indisponible : HTTP ' + response.status);
    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    let buffer = '';
    while (watching) {
      const result = await reader.read();
      if (result.done) break;
      buffer += decoder.decode(result.value, { stream: true });
      let boundary;
      while ((boundary = buffer.indexOf('\n\n')) >= 0) {
        const message = buffer.slice(0, boundary); buffer = buffer.slice(boundary + 2);
        const line = message.split('\n').find(line => line.startsWith('data:'));
        if (!line) continue;
        const event = JSON.parse(line.slice(5).trim());
        if (event.cursor) cursor = event.cursor;
        if (event.eventType) {
          report.events.push({ eventType: event.eventType, cursor: event.cursor });
          console.log(JSON.stringify({ event: event.eventType }));
        }
        for (const notify of listeners) notify();
      }
    }
    if (++reconnects > 8) throw new Error('Trop de reconnexions au flux');
  }
}

async function until(check) {
  return new Promise((resolve, reject) => {
    let busy = false, again = false, settled = false;
    const fail = () => { listeners.delete(notify); reject(new Error('Recette interrompue ou délai dépassé')); };
    const notify = async () => {
      if (busy) { again = true; return; }
      if (settled) return;
      busy = true;
      try {
        do {
          again = false;
          if (streamFailure) throw streamFailure;
          const result = await check();
          if (result) {
            settled = true; listeners.delete(notify); abort.signal.removeEventListener('abort', fail); resolve(result); return;
          }
        } while (again);
      } catch (error) {
        settled = true; listeners.delete(notify); abort.signal.removeEventListener('abort', fail); reject(error);
      } finally { busy = false; }
    };
    listeners.add(notify); abort.signal.addEventListener('abort', fail, { once: true }); notify();
  });
}

function details(row) {
  const value = row.details?.value ?? row.details;
  return typeof value === 'string' ? JSON.parse(value) : value || {};
}

async function diagnose(item) {
  const started = await request(`/cases/${item.caseId}/analyses`, 'POST');
  await until(async () => {
    const analysis = await request('/analyses/' + started.analysisId);
    if (analysis.status === 'FAILED') {
      const traces = await request(`/cases/${item.caseId}/agent-traces`);
      throw new Error('Analyse échouée : ' + JSON.stringify(traces.filter(row => row.status === 'FAILED').map(details)));
    }
    return analysis.status === 'COMPLETED' ? analysis : null;
  });
  const diagnostic = await request(`/analyses/${started.analysisId}/diagnostic`);
  assert.equal(diagnostic.conclusion, 'SUPPORTED', 'Le diagnostic réel doit être étayé');
  return diagnostic;
}

async function pendingProposal(caseId, oldId) {
  return until(async () => {
    const dossier = await request('/cases/' + caseId);
    const proposal = dossier.currentProposal;
    if (proposal && proposal.proposalId !== oldId && proposal.state === 'READY_FOR_APPROVAL') return proposal;
    if (proposal && proposal.proposalId !== oldId && ['CHANGES_REQUIRED', 'STALE'].includes(proposal.state))
      throw new Error('Proposition non publiable : ' + JSON.stringify(proposal.validationReport));
    const traces = await request(`/cases/${caseId}/agent-traces`);
    const failed = traces.filter(row => row.status === 'FAILED' && row.kind !== 'INVESTIGATION');
    if (failed.length) throw new Error('Mission de règle échouée : ' + JSON.stringify(failed.map(details)));
    return null;
  });
}

async function approve(proposal) {
  assert.equal(proposal.validationReport.passed, true);
  assert.equal(proposal.validationReport.publishable, true);
  assert.equal(proposal.validationReport.totalReplayEvaluated, 33);
  return request(`/proposals/${proposal.proposalId}/approval`, 'POST', { revision: proposal.revision, reportId: proposal.validationReport.reportId });
}

async function chat(item, role, text) {
  const started = await request(`/cases/${item.caseId}/expert-messages`, 'POST', { role, text });
  const messages = await until(async () => {
    const traces = await request(`/cases/${item.caseId}/agent-traces`);
    if (traces.some(row => row.mission_id === started.missionId && row.status === 'FAILED')) throw new Error('Tour expert échoué : ' + JSON.stringify(traces.filter(row => row.mission_id === started.missionId).map(details)));
    if (!traces.some(row => row.mission_id === started.missionId && row.status === 'COMPLETED')) return null;
    return request(`/conversations/${started.conversationId}/messages`);
  });
  const reply = messages.filter(message => message.senderRole === role).at(-1);
  assert.ok(reply?.text?.length);
  step('chat-real', { role, missionId: started.missionId, text: reply.text });
  return reply;
}

async function learn(group, withChat = false) {
  const [origin, ...analogues] = group;
  const before = await request('/rules');
  const diagnostic = await diagnose(origin);
  assert.equal(diagnostic.isDeterministic, false);
  assert.equal(diagnostic.createdOrigin, 'INVESTIGATOR');
  await request(`/diagnostics/${diagnostic.diagnosticId}/acceptance`, 'POST', { caseId: origin.caseId });
  let proposal = await pendingProposal(origin.caseId);
  const activated = await approve(proposal);
  const after = await request('/rules');
  const newRule = after.rules.find(rule => !before.rules.some(old => old.ruleId === rule.ruleId));
  assert.ok(newRule?.isActive);
  step('learn-real-rule', { caseId: origin.caseId, diagnosticId: diagnostic.diagnosticId, cause: diagnostic.findings.map(finding => finding.causeCode), ruleId: newRule.ruleId,
    ruleName: newRule.details?.name, publication: activated.newRuleSetVersion, reportId: proposal.validationReport.reportId, positive: proposal.validationReport.positiveCasesPassed, negative: proposal.validationReport.negativeCasesPassed });
  if (withChat) {
    await chat(origin, 'INVESTIGATOR', 'Explique ce diagnostic et cite les preuves consultées. Ne crée ni ne publie de règle.');
    await chat(origin, 'RULE_AGENT', 'Explique pourquoi les conditions de la règle publiée évitent les faux positifs. Ne modifie ni ne publie la règle.');
    const oldId = proposal.proposalId;
    await chat(origin, 'RULE_AGENT', `Révise explicitement la règle publiée ${newRule.ruleId} (operation REVISE, targetRuleId ${newRule.ruleId}). Améliore les questions métier des conditions, sans limiter au site d'origine ni changer la cause. Propose la révision testable, sans publier.`);
    proposal = await pendingProposal(origin.caseId, oldId);
    assert.equal(proposal.operation, 'REVISE');
    const revised = await approve(proposal);
    step('refine-real-rule', { ruleId: newRule.ruleId, publication: revised.newRuleSetVersion, reportId: proposal.validationReport.reportId });
  }
  for (const item of analogues) {
    const result = await diagnose(item);
    assert.equal(result.isDeterministic, true);
    assert.ok(result.findings.some(finding => finding.ruleId === newRule.ruleId));
    const traces = await request(`/cases/${item.caseId}/agent-traces`);
    assert.equal(traces.length, 0, 'Un analogue ne doit appeler aucun LLM');
    step('analogue-dmn', { caseId: item.caseId, ruleId: newRule.ruleId, origin: result.createdOrigin });
  }
  const traces = await request(`/cases/${origin.caseId}/agent-traces`);
  step('actual-agent-measures', { caseId: origin.caseId, measurements: traces.filter(row => details(row).usage).map(row => ({ kind: row.kind, event: row.event_type, usage: details(row).usage })) });
  if (withChat) {
    await chat(origin, 'INVESTIGATOR', 'Correction métier : la clôture administrative LoanClosed ne prouve pas à elle seule la réception physique du retour. Vérifie ReturnReceived et la mise à jour de cette même opération. Cette précision humaine ne modifie pas les journaux.');
    const revised = await request(`/cases/${origin.caseId}/reassessment`, 'POST');
    await until(async () => { const analysis = await request('/analyses/' + revised.analysisId); if (analysis.status === 'FAILED') throw new Error('Réévaluation agentique échouée'); return analysis.status === 'COMPLETED' ? analysis : null; });
    const corrected = await request(`/analyses/${revised.analysisId}/diagnostic`);
    assert.equal(corrected.isDeterministic, false);
    assert.notEqual(corrected.diagnosticId, diagnostic.diagnosticId);
    const evidence = await request(`/analyses/${revised.analysisId}/snapshot`);
    assert.ok(evidence.operatorNotes.some(note => note.source === 'EXPERT_MESSAGE'));
    const pending = await request('/cases/' + origin.caseId);
    assert.equal(pending.status, 'A_REVOIR');
    await request(`/diagnostics/${corrected.diagnosticId}/acceptance`, 'POST', { caseId: origin.caseId });
    step('expert-reassessment-real', { caseId: origin.caseId, analysisId: revised.analysisId, diagnosticId: corrected.diagnosticId, humanNotes: evidence.operatorNotes.length, conclusion: corrected.conclusion });
  }
  return newRule.ruleId;
}

async function groups() {
  const listing = await request('/cases?size=10');
  const result = { location: [], return: [], repair: [] };
  for (const item of listing.items) {
    const dossier = await request('/cases/' + item.caseId);
    if (dossier.symptoms[0].kind === 'LOCATION_MISMATCH') result.location.push(item);
    else {
      const operations = dossier.timeline.filter(event => ['LoanOpened', 'ReturnReceived', 'ReceiptReceived', 'TransferDispatched', 'TransferReceived', 'RepairStarted'].includes(event.eventType));
      result[operations.at(-1).eventType === 'ReturnReceived' ? 'return' : 'repair'].push(item);
    }
  }
  assert.deepEqual(Object.values(result).map(items => items.length), [2, 4, 4]);
  return result;
}

try {
  if (reset) await request('/system/reset', 'POST');
  assert.equal((await request('/system/demo-check')).status, 'READY', 'La recette exige la préparation initiale ; utiliser --reset explicitement');
  cursor = (await request('/sync-snapshot')).cursor;
  observe().catch(error => { if (watching) { streamFailure = error; for (const notify of listeners) notify(); } });
  const initial = await groups();
  for (const item of initial.location) { const diagnostic = await diagnose(item); assert.equal(diagnostic.isDeterministic, true); step('bootstrap-dmn', { caseId: item.caseId }); }
  const returnRuleId = await learn(initial.return, true);
  await learn(initial.repair);
  const retired = await request('/rules/' + returnRuleId + '/retirement', 'POST');
  const retiredRule = await request('/rules/' + returnRuleId);
  assert.equal(retiredRule.rule.active, false);
  step('runtime-retirement', { ruleId: returnRuleId, publication: retired.newRuleSetVersion });
  if (allOrders) {
    await request('/system/reset', 'POST');
    cursor = (await request('/sync-snapshot')).cursor;
    const reversed = await groups();
    await learn(reversed.repair);
    await learn(reversed.return);
    step('inverse-order', { passed: true });
  }
  report.passed = true;
} catch (error) {
  report.passed = false; report.failure = error.message;
  console.error(JSON.stringify({ recipeFailed: error.message }));
  process.exitCode = 1;
} finally {
  watching = false; clearTimeout(timeout); abort.abort();
  if (restore) {
    const response = await fetch(base + '/system/reset', { method: 'POST', signal: AbortSignal.timeout(30000) });
    if (!response.ok) { report.restoreFailed = true; process.exitCode = 1; }
    else report.restoredEpoch = (await response.json()).newEpoch;
  }
  report.finishedAt = new Date().toISOString();
  if (output) fs.writeFileSync(output, JSON.stringify(report, null, 2) + '\n');
  console.log(JSON.stringify({ passed: report.passed, report: output, restored: report.restoredEpoch, failure: report.failure }));
}