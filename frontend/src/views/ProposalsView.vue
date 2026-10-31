<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
      <h1 style="font-size: 1.5rem; font-weight: 700;">Propositions de règles synthétisées par les agents</h1>
    </div>

    <div v-if="proposals.length === 0" class="card" style="text-align: center; padding: 3rem; color: var(--text-muted);">
      <div>Aucune proposition de règle pour le moment.</div>
      <div style="font-size: 0.85rem; margin-top: 0.5rem;">
        Les propositions sont générées automatiquement par l'agent de règles lorsqu'un diagnostic d'investigation est accepté.
      </div>
    </div>

    <div v-else style="display: flex; flex-direction: column; gap: 1.25rem;">
      <div v-for="p in proposals" :key="p.proposalId" class="card">
        <div class="card-header">
          <div style="display: flex; align-items: center; gap: 0.75rem;">
            <span class="badge badge-purple">{{ p.operation }}</span>
            <span class="card-title">{{ p.metadata?.name || p.targetRuleId }}</span>
            <span class="badge badge-blue">{{ p.metadata?.causeCode }}</span>
          </div>
          <span :class="p.status === 'PUBLISHED' ? 'badge badge-green' : 'badge badge-amber'">
            {{ p.status }}
          </span>
        </div>

        <div style="margin-bottom: 1rem; font-size: 0.9rem;">
          <div><strong>Justification :</strong> {{ p.rationale?.rationale }}</div>
          <div style="font-size: 0.8rem; color: var(--text-muted); margin-top: 0.25rem;">
            Proposé le {{ formatDate(p.createdAt) }} • Base référentiel : v{{ p.baseRuleSetVersion }}
          </div>
        </div>

        <!-- DMN Source Code -->
        <div style="margin-bottom: 1rem;">
          <div style="font-size: 0.75rem; font-weight: 600; color: var(--text-muted); margin-bottom: 0.25rem;">Modèle DMN proposé :</div>
          <pre class="code-block" style="max-height: 180px;">{{ p.dmnXml }}</pre>
        </div>

        <div v-if="p.validationReport" style="font-size: 0.85rem; margin-bottom: 0.75rem;">
          Positifs : {{ p.validationReport.positiveCasesPassed }} / {{ p.validationReport.positiveCasesTested }} ·
          Négatifs : {{ p.validationReport.negativeCasesPassed }} / {{ p.validationReport.negativeCasesTested }} ·
          Replay : {{ p.validationReport.totalReplayEvaluated }} · Régressions : {{ p.validationReport.regressionsDetected }}
        </div>
        <div style="display: flex; justify-content: flex-end; gap: 0.75rem;" v-if="p.status === 'READY_FOR_APPROVAL' && p.validationReport?.publishable">
          <button @click="publish(p)" class="btn btn-primary btn-sm">
            🚀 Approuver & Publier au Runtime
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStockGapStore } from '../stores/stockGapStore';

const store = useStockGapStore();
const proposals = ref([]);

async function loadProposals() {
  try {
    const res = await fetch('/api/proposals');
    if (res.ok) {
      proposals.value = await res.json();
    }
  } catch (e) {
    console.error('Erreur chargement propositions', e);
  }
}

async function publish(p) {
  try {
    const res = await fetch(`/api/proposals/${p.proposalId}/approval`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ revision: p.currentRevision, reportId: p.validationReport.reportId })
    });
    if (res.ok) {
      loadProposals();
      store.fetchOverview();
      store.fetchSyncSnapshot();
    }
  } catch (e) {
    console.error('Erreur publication', e);
  }
}

function formatDate(iso) {
  if (!iso) return '';
  return new Date(iso).toLocaleDateString('fr-FR', {
    hour: '2-digit', minute: '2-digit'
  });
}

onMounted(() => {
  loadProposals();
});
</script>
