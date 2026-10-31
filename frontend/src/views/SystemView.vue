<template>
  <div>
    <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 1.25rem;">État du système & Réinitialisation</h1>

    <div class="system-summary">
      <div class="card" style="margin-bottom: 0;">
        <div class="card-header">
          <span class="card-title">État des composants locaux</span>
          <span class="badge badge-green">Opérationnel</span>
        </div>

        <div style="display: flex; flex-direction: column; gap: 0.75rem; font-size: 0.9rem;">
          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Backend Spring Boot :</span>
            <span style="color: var(--accent-green); font-weight: 600;">UP (Port 8080)</span>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Moteur DMN / FEEL :</span>
            <span style="color: var(--accent-blue); font-weight: 600;">Apache KIE 10.2.0</span>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Base de données :</span>
            <span style="color: var(--text-main);">PostgreSQL 18 (Schémas runtime / evaluation)</span>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Transport asynchrone :</span>
            <span style="color: var(--text-main);">Redpanda / Kafka (Port 9092)</span>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Worker Python :</span>
            <span style="color: var(--accent-purple); font-weight: 600;">OpenAI Responses / Confluent-Kafka</span>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Flux temps réel SSE :</span>
            <span :style="{ color: store.connected ? 'var(--accent-green)' : 'var(--accent-rose)', fontWeight: 600 }">
              {{ store.connected ? 'Connecté (Live cursor)' : 'Déconnecté' }}
            </span>
          </div>
        </div>
      </div>

      <div class="card" style="margin-bottom: 0;">
        <div class="card-header">
          <span class="card-title">Époque active du dataset</span>
          <span class="badge badge-blue">Epoch runtime</span>
        </div>

        <div style="display: flex; flex-direction: column; gap: 0.75rem; font-size: 0.9rem;">
          <div>
            <div style="font-size: 0.75rem; color: var(--text-muted); text-transform: uppercase;">UUID de l'époque active :</div>
            <code style="display: block; background: #090d16; padding: 0.5rem; border-radius: 4px; font-size: 0.85rem; margin-top: 0.25rem; overflow-wrap: anywhere;">
              {{ store.overview?.datasetEpoch || 'Non initialisée' }}
            </code>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Version du référentiel actif :</span>
            <strong>v{{ store.activeRuleSetVersion }}</strong>
          </div>

          <div style="display: flex; justify-content: space-between;">
            <span style="color: var(--text-muted);">Curseur durable SSE :</span>
            <code style="font-size: 0.8rem;">{{ store.cursor || '0' }}</code>
          </div>

          <div style="font-size: 0.8rem; color: var(--text-muted); line-height: 1.4; border-top: 1px solid var(--border-color); padding-top: 0.75rem;">
            ℹ️ Toute importation ou réinitialisation crée une époque neuve. Les analyses et curseurs passés sont isolés.
          </div>
        </div>
      </div>
    </div>

    <section class="demo-preparation">
      <div class="demo-preparation__header">
        <h2>Préparation initiale de la démo</h2>
        <div class="demo-preparation__actions">
          <span v-if="demoCheck" :class="demoCheck.status === 'READY' ? 'badge badge-green' : 'badge badge-amber'">
            {{ demoCheck.status === 'READY' ? 'Prête' : 'Remise à zéro requise' }}
          </span>
          <button class="btn btn-secondary btn-sm" :disabled="checking || resetting" @click="loadDemoCheck">
            {{ checking ? 'Vérification en cours...' : 'Vérifier la préparation' }}
          </button>
        </div>
      </div>
      <p v-if="checkError" class="demo-preparation__error" role="alert">{{ checkError }}</p>
      <table v-if="demoCheck" class="demo-preparation__checks">
        <tbody>
          <tr v-for="(label, key) in checkLabels" :key="key">
            <td>{{ label }}</td>
            <td :style="{ color: demoCheck.checks[key] ? 'var(--accent-green)' : 'var(--accent-amber)' }">
              {{ demoCheck.checks[key] ? 'Conforme' : 'À vérifier' }}
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="demoCheck" class="demo-preparation__counts">
        <span>{{ demoCheck.counts.virginCases }} / {{ demoCheck.counts.cases }} dossiers non analysés</span>
        <span>{{ demoCheck.counts.activeRules }} règle active</span>
        <span v-if="demoCheck.coverageEvaluated">{{ demoCheck.deterministicCases }} couverts · {{ demoCheck.uncoveredCases }} hors règle</span>
      </div>
    </section>

    <!-- Reset Box -->
    <div class="card" style="border: 1px solid var(--accent-rose); background: rgba(244, 63, 94, 0.05);">
      <div class="card-header">
        <span class="card-title" style="color: var(--accent-rose);">Zone d'administration — Réinitialisation complète</span>
        <span class="badge badge-rose">Action destructrice</span>
      </div>

      <p style="font-size: 0.9rem; margin-bottom: 1rem; color: var(--text-main);">
        La réinitialisation recharge les 10 exemplaires et les 10 dossiers non analysés du jeu <code>demo</code>, avec uniquement <code>R001 v1</code>. Elle conserve l'historique dans les anciennes époques et ne modifie ni votre clé API locale ni votre configuration.
      </p>

      <div style="display: flex; justify-content: flex-end;">
        <button :disabled="resetting" @click="handleReset" class="btn btn-danger">
          {{ resetting ? 'Réinitialisation en cours...' : '🔄 Réinitialiser le jeu de données et les règles' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStockGapStore } from '../stores/stockGapStore';

const store = useStockGapStore();
const resetting = ref(false);
const checking = ref(false);
const demoCheck = ref(null);
const checkError = ref('');
const checkLabels = {
  expectedDataset: 'Version et empreinte du jeu public',
  tenCasesAndCopies: '10 dossiers et 10 exemplaires',
  allCasesVirgin: 'Tous les dossiers non analysés',
  onlyBootstrapRule: 'R001 v1 seule active',
  noAnalyses: 'Aucune analyse enregistrée',
  noMissions: 'Aucune mission d’agent',
  noConversations: 'Aucune conversation',
  noProposals: 'Aucune proposition de règle',
  expectedCoverage: '2 dossiers couverts, 8 hors règle',
};

async function loadDemoCheck() {
  checking.value = true;
  checkError.value = '';
  try {
    const response = await fetch('/api/system/demo-check');
    if (!response.ok) throw new Error(`Vérification indisponible (HTTP ${response.status}).`);
    demoCheck.value = await response.json();
  } catch (error) {
    demoCheck.value = null;
    checkError.value = error.message || 'Vérification indisponible.';
  } finally {
    checking.value = false;
  }
}

onMounted(loadDemoCheck);

async function handleReset() {
  if (!confirm("Remettre la démo à zéro ? Les 10 dossiers seront non analysés, avec R001 seule active. L'historique sera conservé dans les anciennes époques.")) {
    return;
  }

  resetting.value = true;
  try {
    const res = await fetch('/api/system/reset', { method: 'POST' });
    if (res.ok) {
      await store.fetchOverview();
      await store.fetchSyncSnapshot();
      await loadDemoCheck();
      if (demoCheck.value?.status === 'READY') {
        alert("Démo prête : 10 dossiers non analysés, R001 seule et couverture 2 / 8 vérifiée.");
      } else {
        alert("Le jeu a été rechargé, mais les vérifications de préparation ne sont pas toutes conformes.");
      }
    } else {
      alert("Erreur lors de la réinitialisation.");
    }
  } catch (e) {
    console.error('Erreur reset', e);
    alert("Erreur réseau lors de la réinitialisation.");
  } finally {
    resetting.value = false;
  }
}
</script>

<style scoped>
.system-summary {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(min(100%, 22rem), 1fr));
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}
.demo-preparation {
  border-top: 1px solid var(--border-color);
  padding: 1rem 0;
  margin-bottom: 1rem;
}
.demo-preparation__header,
.demo-preparation__actions,
.demo-preparation__counts {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 0.75rem;
}
.demo-preparation__header {
  justify-content: space-between;
}
.demo-preparation h2 {
  font-size: 1.1rem;
  margin: 0;
}
.demo-preparation__actions button {
  min-width: 12rem;
  max-width: 100%;
}
.demo-preparation__checks {
  width: 100%;
  border-collapse: collapse;
  margin: 1rem 0;
  font-size: 0.9rem;
}
.demo-preparation__checks td {
  padding: 0.5rem 0;
  border-bottom: 1px solid var(--border-color);
}
.demo-preparation__checks td:last-child {
  text-align: right;
}
.demo-preparation__counts {
  color: var(--text-muted);
  font-size: 0.85rem;
}
.demo-preparation__error {
  color: var(--accent-rose);
}
.system-summary code {
  overflow-wrap: anywhere;
}
@media (max-width: 760px) {
  .system-summary .card-header,
  .system-summary .card > div:not(.card-header) > div {
    flex-wrap: wrap;
    gap: 0.5rem;
  }
  button {
    white-space: normal;
    overflow-wrap: anywhere;
  }
}
</style>
