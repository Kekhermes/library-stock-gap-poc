<template>
  <div v-if="dossier">
    <!-- Header -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
      <div style="display: flex; align-items: center; gap: 1rem;">
        <router-link to="/cases" class="btn btn-secondary btn-sm">← Retour aux cas</router-link>
        <h1 style="font-size: 1.3rem; font-weight: 700;">
          Dossier : <span style="font-family: monospace; color: var(--accent-blue);">{{ dossier.barcode }}</span>
        </h1>
        <span style="color: var(--text-muted);">{{ dossier.title }}</span>
      </div>

      <div style="display: flex; align-items: center; gap: 0.75rem;">
        <span :class="getStatusBadgeClass(dossier.status)">{{ formatStatus(dossier.status) }}</span>
        <button v-if="dossier.status !== 'EN_ANALYSE'" @click="startAnalysis" class="btn btn-primary btn-sm">
          ⚡ Analyser le cas
        </button>
        <button v-else @click="cancelAnalysis" class="btn btn-danger btn-sm">
          Annuler l'analyse
        </button>
      </div>
    </div>

    <!-- 3-Panel Layout -->
    <div class="dossier-layout">
      <!-- 1. LEFT PANEL: FAITS -->
      <div class="dossier-panel">
        <div class="panel-header">
          <span>📋 1. Faits & Sources figées</span>
          <span class="badge badge-blue">{{ dossier.owningSiteId }}</span>
        </div>

        <div class="panel-body">
          <!-- Système vs Observation -->
          <div style="margin-bottom: 1.25rem;">
            <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); margin-bottom: 0.5rem; text-transform: uppercase;">
              Comparaison d'inventaire
            </div>

            <div style="background: rgba(0,0,0,0.2); padding: 0.75rem; border-radius: 6px; border: 1px solid var(--border-color); margin-bottom: 0.5rem;">
              <div style="font-size: 0.75rem; color: var(--text-muted);">Attendu (Système informatique) :</div>
              <div style="font-weight: 600; color: var(--accent-amber);">
                Statut: {{ dossier.systemState?.status || 'NON RENSEIGNÉ' }} | Site: {{ dossier.systemState?.siteId }}
              </div>
            </div>

            <div style="background: rgba(0,0,0,0.2); padding: 0.75rem; border-radius: 6px; border: 1px solid var(--border-color);">
              <div style="font-size: 0.75rem; color: var(--text-muted);">Observé (Scan physique) :</div>
              <div v-if="dossier.observations?.length > 0">
                <div v-for="obs in dossier.observations" :key="obs.observationId" style="font-weight: 600; color: var(--accent-green);">
                  Site: {{ obs.siteId }} | Qualité: {{ obs.quality }} | Date: {{ formatDate(obs.observedAt) }}
                </div>
              </div>
              <div v-else style="color: var(--accent-rose); font-style: italic;">
                Aucune observation physique enregistrée.
              </div>
            </div>
          </div>

          <!-- Symptômes constatés -->
          <div style="margin-bottom: 1.25rem;">
            <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); margin-bottom: 0.5rem; text-transform: uppercase;">
              Symptômes d'écart ({{ dossier.symptoms?.length }})
            </div>
            <div style="display: flex; flex-direction: column; gap: 0.35rem;">
              <div v-for="sym in dossier.symptoms" :key="sym.symptomId"
                   style="background: rgba(251, 191, 36, 0.08); border: 1px solid rgba(251, 191, 36, 0.25); padding: 0.5rem; border-radius: 4px; font-size: 0.85rem;">
                <span class="badge badge-amber">{{ sym.kind }}</span>
                <span v-if="sym.expectedValue" style="margin-left: 0.5rem; color: var(--text-muted);">
                  Attendu: {{ sym.expectedValue }}
                </span>
                <span v-if="sym.observedValue" style="margin-left: 0.5rem; color: var(--text-muted);">
                  Observé: {{ sym.observedValue }}
                </span>
              </div>
            </div>
          </div>

          <!-- Notes opérateur -->
          <div style="margin-bottom: 1.25rem;" v-if="dossier.operatorNotes?.length > 0">
            <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); margin-bottom: 0.5rem; text-transform: uppercase;">
              Notes opérateur libres (Données non FEEL)
            </div>
            <div v-for="note in dossier.operatorNotes" :key="note.noteId"
                 style="background: rgba(148, 163, 184, 0.08); border-left: 3px solid var(--text-muted); padding: 0.5rem 0.75rem; margin-bottom: 0.5rem; font-size: 0.85rem; font-style: italic;">
              "{{ note.text }}"
              <div style="font-size: 0.75rem; color: var(--text-muted); margin-top: 0.25rem;">{{ formatDate(note.recordedAt) }}</div>
            </div>
          </div>

          <!-- Chronologie des événements -->
          <div>
            <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); margin-bottom: 0.5rem; text-transform: uppercase;">
              Chronologie des événements source ({{ dossier.timeline?.length }})
            </div>
            <div style="display: flex; flex-direction: column; gap: 0.5rem; font-size: 0.8rem;">
              <div v-for="ev in dossier.timeline" :key="ev.sourceEventId"
                   style="padding: 0.5rem; border: 1px solid var(--border-color); border-radius: 4px; background: rgba(0,0,0,0.1);">
                <div style="display: flex; justify-content: space-between; font-weight: 600;">
                  <span style="color: var(--accent-blue);">{{ ev.eventType }}</span>
                  <span style="color: var(--text-muted);">{{ formatDate(ev.occurredAt) }}</span>
                </div>
                <div style="color: var(--text-muted); font-size: 0.75rem; margin-top: 0.2rem; font-family: monospace;">
                  {{ JSON.stringify(ev.payload) }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 2. CENTER PANEL: RESULTAT & CONNAISSANCE -->
      <div class="dossier-panel">
        <div class="panel-header">
          <span>🧠 2. Diagnostic & Règles DMN</span>
          <span v-if="dossier.currentDiagnostic" :class="dossier.currentDiagnostic.isDeterministic ? 'badge badge-green' : 'badge badge-purple'">
            {{ dossier.currentDiagnostic.isDeterministic ? 'Déterministe DMN' : 'Investigateur IA' }}
          </span>
        </div>

        <div class="panel-body">
          <div v-if="dossier.analyses?.[0]?.status === 'AGENT_PENDING' || dossier.analyses?.[0]?.status === 'AGENT_RUNNING'" class="agent-progress">
            <div class="agent-progress__header">
              <div class="agent-orb" aria-hidden="true"><span></span></div>
              <div>
                <div class="eyebrow">Cascade hybride</div>
                <div class="agent-progress__title">Investigation GPT en cours</div>
                <div class="agent-progress__copy">La première passe DMN est terminée. L’agent analyse maintenant le dossier de preuves figé.</div>
              </div>
              <span class="badge badge-purple">AGENT</span>
            </div>
            <div class="fallback-reason">
              <span>Relais déclenché</span>
              <strong>{{ fallbackLabel(dossier.analyses[0].fallbackReason) }}</strong>
            </div>
            <div class="analysis-steps" aria-label="Progression de l'analyse">
              <span class="done">✓ Règles DMN évaluées</span>
              <span class="active"><i></i> Investigation GPT</span>
              <span>Diagnostic à produire</span>
            </div>
          </div>
          <div v-else-if="dossier.analyses?.[0]?.status === 'FAILED'" style="border: 1px solid var(--accent-rose); border-radius: 6px; padding: 0.85rem; background: rgba(251, 113, 133, 0.08); margin-bottom: 1rem; font-size: 0.85rem; color: var(--text-muted);">
            <template v-if="dossier.analyses[0].deterministicTrace?.phase === 'DETERMINISTIC'">
              <strong style="color: var(--accent-rose);">Évaluation déterministe en échec</strong><br>
              Le moteur DMN n'a pas pu terminer l'évaluation. Aucun agent LLM n'a été lancé pour masquer cette panne.
              <div role="alert" style="margin-top: 0.5rem; overflow-wrap: anywhere;">{{ dossier.analyses[0].deterministicTrace.message }}</div>
            </template>
            <template v-else>
              <strong style="color: var(--accent-rose);">Investigation agentique en échec</strong><br>
              La cascade DMN → agent a bien été déclenchée, mais l'agent n'a pas produit de diagnostic recevable. Le cas peut être relancé.
            </template>
          </div>

          <div v-else-if="dossier.status === 'EN_ANALYSE'" style="text-align: center; padding: 2rem;">
            <div style="font-size: 2rem; margin-bottom: 1rem;">⚙️</div>
            <div style="font-weight: 600;">Évaluation déterministe en cours…</div>
            <div style="color: var(--text-muted); font-size: 0.85rem; margin-top: 0.5rem;">
              1. Évaluation DMN par Apache KIE<br>
              2. Fallback investigation via Kafka si nécessaire
            </div>
          </div>

          <!-- Diagnostic disponible -->
          <div v-else-if="dossier.currentDiagnostic">
            <div style="background: rgba(0,0,0,0.25); border: 1px solid var(--border-color); border-radius: 6px; padding: 1rem; margin-bottom: 1rem;">
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.75rem;">
                <span style="font-weight: 700; font-size: 1.1rem;">Synthèse diagnostique</span>
                <span :class="getEpistemicBadge(dossier.currentDiagnostic.conclusion)">
                  {{ dossier.currentDiagnostic.conclusion }}
                </span>
              </div>

              <p style="font-size: 0.95rem; margin-bottom: 0.75rem;">
                {{ dossier.currentDiagnostic.summary }}
              </p>

              <!-- Findings -->
              <div v-for="(f, idx) in dossier.currentDiagnostic.findings" :key="idx"
                   style="border-top: 1px solid var(--border-color); padding-top: 0.75rem; margin-top: 0.75rem;">
                <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.4rem;">
                  <span class="badge badge-blue">{{ f.causeCode }}</span>
                  <span style="font-size: 0.8rem; color: var(--text-muted);">
                    Règle: {{ f.ruleId || 'Investigation IA' }}
                  </span>
                </div>
                <div v-for="(ast, aidx) in f.assertions" :key="aidx" style="font-size: 0.85rem; margin-bottom: 0.25rem;">
                  • {{ ast }}
                </div>
                <div v-if="f.recommendedChecks?.length > 0" style="margin-top: 0.5rem; font-size: 0.8rem; color: var(--text-muted);">
                  <strong>Recommandations :</strong> {{ f.recommendedChecks.join(', ') }}
                </div>
              </div>

              <div v-if="dossier.currentDiagnostic.missingEvidence?.length > 0"
                   style="border-top: 1px solid var(--border-color); padding-top: 0.75rem; margin-top: 0.75rem; font-size: 0.8rem; color: var(--accent-amber);">
                <strong>Preuves manquantes :</strong> {{ dossier.currentDiagnostic.missingEvidence.join(', ') }}
              </div>

              <!-- Bouton d'acceptation -->
              <div v-if="dossier.status === 'A_REVOIR'" style="margin-top: 1.25rem; padding-top: 0.75rem; border-top: 1px solid var(--border-color); display: flex; justify-content: flex-end;">
                <button @click="acceptDiagnostic" class="btn btn-success btn-sm">
                  ✓ Accepter ce diagnostic
                </button>
              </div>
            </div>

            <!-- Diagnostic accepté sans règle formalisable -->
            <div v-if="dossier.status === 'DIAGNOSTIC_ACCEPTE' && !dossier.currentProposal"
                 style="border: 1px solid var(--accent-amber); border-radius: 6px; padding: 0.85rem; background: rgba(251, 191, 36, 0.06); margin-top: 1.25rem; font-size: 0.85rem; color: var(--text-muted);">
              <template v-if="dossier.currentDiagnostic?.isDeterministic">
                <strong style="color: var(--accent-green);">Diagnostic déterministe accepté</strong><br>
                Ce diagnostic provient d'une règle DMN active ; aucune synthèse de règle n'est déclenchée.
              </template>
              <template v-else-if="dossier.currentDiagnostic && dossier.currentDiagnostic.conclusion !== 'SUPPORTED'">
                <strong style="color: var(--accent-amber);">Diagnostic accepté non formalisable ({{ dossier.currentDiagnostic.conclusion }})</strong><br>
                Aucune règle n'est proposée : les preuves disponibles ne permettent pas une règle FEEL publiable.
                Le dossier conserve ses preuves et ses recommandations de vérification.
              </template>
              <template v-else>
                ⏳ Synthèse de règle en cours...
              </template>
            </div>

            <!-- Proposition de règle synthétisée -->
            <div v-if="dossier.currentProposal" style="border: 1px solid var(--accent-purple); border-radius: 6px; padding: 1rem; background: rgba(192, 132, 252, 0.05); margin-top: 1.25rem;">
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.75rem;">
                <div>
                  <span class="badge badge-purple" style="margin-right: 0.5rem;">{{ dossier.currentProposal.operation }}</span>
                  <strong>{{ dossier.currentProposal.ruleName }}</strong>
                </div>
                <span class="badge badge-amber">{{ dossier.currentProposal.state }}</span>
              </div>

              <div style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 0.75rem;">
                <strong>Justification :</strong> {{ dossier.currentProposal.rationale }}
              </div>

              <!-- DMN Source Code -->
              <div style="margin-bottom: 1rem;">
                <div style="font-size: 0.75rem; font-weight: 600; color: var(--text-muted); margin-bottom: 0.25rem;">Modèle DMN 1.5 généré :</div>
                <pre class="code-block" style="max-height: 150px;">{{ dossier.currentProposal.dmnXml }}</pre>
              </div>

              <!-- Actions de publication -->
              <div v-if="dossier.currentProposal.state === 'READY_FOR_APPROVAL'" style="display: flex; justify-content: space-between; align-items: center;">
                <span style="font-size: 0.8rem; color: var(--accent-green);">
                  ✓ Tests de replay validés
                </span>
                <button @click="approveProposal" class="btn btn-primary btn-sm">
                  🚀 Approuver & Publier au Runtime
                </button>
              </div>
              <div v-else-if="dossier.currentProposal.state === 'PUBLISHED'" style="color: var(--accent-green); font-size: 0.85rem; font-weight: 600;">
                ✓ Règle publiée et active au runtime (v{{ store.activeRuleSetVersion }})
              </div>
            </div>
          </div>

          <div v-else style="text-align: center; padding: 3rem; color: var(--text-muted);">
            <div>Aucun diagnostic pour ce dossier.</div>
            <button @click="startAnalysis" class="btn btn-primary btn-sm" style="margin-top: 1rem;">
              Lancer l'analyse déterministe
            </button>
          </div>
        </div>
      </div>

      <!-- 3. RIGHT PANEL: ECHANGE & CHAT EXPERT -->
      <div class="dossier-panel">
        <div class="panel-header">
          <span>💬 3. Discussion Expert</span>
          <span class="badge badge-blue">Admin & Agents</span>
        </div>

        <div class="panel-body" style="display: flex; flex-direction: column;">
          <div style="flex: 1; overflow-y: auto; display: flex; flex-direction: column; gap: 0.75rem; margin-bottom: 1rem;">
            <div v-if="messages.length === 0" style="color: var(--text-muted); font-style: italic; font-size: 0.85rem; text-align: center; margin-top: 2rem;">
              Posez des questions ou discutez des règles avec l'agent d'investigation ou de règles.
            </div>

            <div v-for="msg in messages" :key="msg.messageId"
                 :style="{
                   alignSelf: msg.senderRole === 'Admin' ? 'flex-end' : 'flex-start',
                   maxWidth: '85%',
                   background: msg.senderRole === 'Admin' ? '#0284c7' : 'var(--bg-tertiary)',
                   color: 'white',
                   padding: '0.6rem 0.85rem',
                   borderRadius: '8px',
                   fontSize: '0.85rem'
                 }">
              <div style="font-size: 0.7rem; opacity: 0.75; margin-bottom: 0.2rem; font-weight: 600;">
                {{ msg.senderRole }} • {{ formatDate(msg.sentAt) }}
              </div>
              <div>{{ msg.text }}</div>
            </div>
          </div>

          <!-- Message Input -->
          <div style="display: flex; gap: 0.5rem; border-top: 1px solid var(--border-color); padding-top: 0.75rem;">
            <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="Message à l'agent..." style="flex: 1;" />
            <button @click="sendMessage" :disabled="!newMessage.trim()" class="btn btn-primary btn-sm">
              Envoyer
            </button>
          </div>

          <div style="font-size: 0.7rem; color: var(--text-muted); margin-top: 0.5rem; text-align: center;">
            ⚠️ Un message de chat ne vaut pas approbation applicative.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStockGapStore } from '../stores/stockGapStore';

const route = useRoute();
const store = useStockGapStore();

const dossier = ref(null);
const messages = ref([]);
const newMessage = ref('');
let pollInterval = null;

async function loadDossier() {
  const caseId = route.params.caseId;
  try {
    const res = await fetch(`/api/cases/${caseId}`);
    if (res.ok) {
      dossier.value = await res.json();
      if (dossier.value.activeConversationId) {
        loadMessages(dossier.value.activeConversationId);
      }
    }
  } catch (e) {
    console.error('Erreur chargement dossier', e);
  }
}

async function loadMessages(conversationId) {
  try {
    const res = await fetch(`/api/conversations/${conversationId}/messages`);
    if (res.ok) {
      messages.value = await res.json();
    }
  } catch (e) {
    console.error('Erreur chargement messages', e);
  }
}

async function startAnalysis() {
  const caseId = route.params.caseId;
  try {
    const res = await fetch(`/api/cases/${caseId}/analyses`, { method: 'POST' });
    if (res.ok) {
      loadDossier();
    }
  } catch (e) {
    console.error('Erreur lancement analyse', e);
  }
}

async function cancelAnalysis() {
  const analysis = dossier.value?.analyses?.[0];
  if (!analysis) return;
  try {
    await fetch(`/api/analyses/${analysis.analysisId}/cancellation`, { method: 'POST' });
    loadDossier();
  } catch (e) {
    console.error('Erreur annulation', e);
  }
}

async function acceptDiagnostic() {
  const diag = dossier.value?.currentDiagnostic;
  if (!diag) return;
  try {
    const res = await fetch(`/api/diagnostics/${diag.diagnosticId}/acceptance`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ caseId: dossier.value.caseId })
    });
    if (res.ok) {
      loadDossier();
      store.fetchOverview();
    }
  } catch (e) {
    console.error('Erreur acceptation', e);
  }
}

async function approveProposal() {
  const prop = dossier.value?.currentProposal;
  if (!prop) return;
  try {
    const res = await fetch(`/api/proposals/${prop.proposalId}/approval`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ revision: prop.revision, reportId: prop.proposalId })
    });
    if (res.ok) {
      loadDossier();
      store.fetchOverview();
      store.fetchSyncSnapshot();
    }
  } catch (e) {
    console.error('Erreur approbation proposition', e);
  }
}

async function sendMessage() {
  if (!newMessage.value.trim() || !dossier.value?.activeConversationId) return;
  const convId = dossier.value.activeConversationId;
  const text = newMessage.value.trim();
  newMessage.value = '';

  try {
    const res = await fetch(`/api/conversations/${convId}/messages`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ text, role: 'Admin' })
    });
    if (res.ok) {
      loadMessages(convId);
    }
  } catch (e) {
    console.error('Erreur envoi message', e);
  }
}

function getStatusBadgeClass(status) {
  switch (status) {
    case 'A_ANALYSER': return 'badge badge-amber';
    case 'EN_ANALYSE': return 'badge badge-blue';
    case 'A_REVOIR': return 'badge badge-purple';
    case 'DIAGNOSTIC_ACCEPTE': return 'badge badge-green';
    default: return 'badge badge-secondary';
  }
}

function formatStatus(status) {
  switch (status) {
    case 'A_ANALYSER': return 'À analyser';
    case 'EN_ANALYSE': return 'En analyse';
    case 'A_REVOIR': return 'À revoir';
    case 'DIAGNOSTIC_ACCEPTE': return 'Diagnostic accepté';
    default: return status;
  }
}

function getEpistemicBadge(conclusion) {
  switch (conclusion) {
    case 'SUPPORTED': return 'badge badge-green';
    case 'PARTIAL': return 'badge badge-amber';
    case 'HYPOTHESIS': return 'badge badge-amber';
    case 'INDETERMINATE': return 'badge badge-rose';
    case 'CONFLICT': return 'badge badge-rose';
    case 'CONTRADICTED': return 'badge badge-rose';
    default: return 'badge badge-secondary';
  }
}

function formatDate(iso) {
  if (!iso) return '';
  try {
    return new Date(iso).toLocaleDateString('fr-FR', {
      hour: '2-digit', minute: '2-digit', second: '2-digit'
    });
  } catch (e) {
    return iso;
  }
}

function fallbackLabel(raw) {
  const labels = {
    NO_RULE_MATCH: 'Aucune règle active ne correspond au dossier',
    PARTIAL_COVERAGE: 'Les règles actives ne couvrent qu’une partie du dossier'
  };
  try {
    const parsed = typeof raw === 'string' ? JSON.parse(raw) : raw;
    return labels[parsed?.reason] || parsed?.reason || 'Couverture déterministe insuffisante';
  } catch {
    return labels[raw] || 'Couverture déterministe insuffisante';
  }
}

onMounted(() => {
  loadDossier();
  pollInterval = setInterval(loadDossier, 3000);
});

onUnmounted(() => {
  if (pollInterval) clearInterval(pollInterval);
});
</script>

<style scoped>
.agent-progress { padding: 1.25rem; border: 1px solid rgba(167, 139, 250, .45); border-radius: 8px; background: linear-gradient(135deg, rgba(139, 92, 246, .10), rgba(30, 41, 59, .35)); }
.agent-progress__header { display: grid; grid-template-columns: 42px 1fr auto; gap: .85rem; align-items: center; }
.agent-orb { width: 42px; height: 42px; border-radius: 50%; display: grid; place-items: center; background: rgba(167,139,250,.16); border: 1px solid rgba(167,139,250,.5); }
.agent-orb span { width: 11px; height: 11px; border-radius: 50%; background: var(--accent-purple); animation: pulse 1.25s ease-in-out infinite; }
.eyebrow { color: var(--accent-purple); font-size: .68rem; font-weight: 700; letter-spacing: .08em; text-transform: uppercase; }
.agent-progress__title { font-size: 1rem; font-weight: 700; margin-top: .1rem; }
.agent-progress__copy { color: var(--text-muted); font-size: .82rem; line-height: 1.45; margin-top: .25rem; }
.fallback-reason { display: flex; justify-content: space-between; gap: 1rem; padding: .65rem .75rem; margin-top: 1rem; border-radius: 5px; background: rgba(15,23,42,.45); font-size: .78rem; color: var(--text-muted); }
.fallback-reason strong { color: var(--accent-amber); text-align: right; }
.analysis-steps { display: flex; gap: .5rem; align-items: center; margin-top: .9rem; font-size: .72rem; color: var(--text-muted); }
.analysis-steps span { flex: 1; white-space: nowrap; }
.analysis-steps span:not(:last-child)::after { content: '→'; float: right; color: var(--border-color); }
.analysis-steps .done { color: var(--accent-green); }.analysis-steps .active { color: #d8b4fe; font-weight: 600; }.analysis-steps i { display: inline-block; width: 7px; height: 7px; margin-right: 4px; border-radius: 50%; background: #d8b4fe; animation: pulse 1.25s ease-in-out infinite; }
@keyframes pulse { 50% { transform: scale(1.65); opacity: .45; } }
@media (prefers-reduced-motion: reduce) { .agent-orb span, .analysis-steps i { animation: none; } }
</style>
