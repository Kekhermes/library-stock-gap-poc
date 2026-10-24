<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
      <div>
        <h1 style="font-size: 1.5rem; font-weight: 700;">Catalogue des règles de diagnostic</h1>
        <div style="color: var(--text-muted); font-size: 0.85rem;">
          Référentiel actif : <strong>v{{ activeVersion }}</strong> • Modification réservée à l'agent IA • Retrait par l'Admin
        </div>
      </div>
    </div>

    <div class="card">
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Identifiant</th>
              <th>Nom de la règle</th>
              <th>Cause diagnostiquée</th>
              <th>Version max</th>
              <th>Statut dans le référentiel actif</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="r in rules" :key="r.ruleId">
              <td>
                <router-link :to="`/rules/${r.ruleId}`" style="font-family: monospace; font-weight: 600;">
                  {{ r.ruleId }}
                </router-link>
              </td>
              <td>{{ r.details?.name || r.ruleId }}</td>
              <td>
                <span class="badge badge-blue">{{ r.details?.causeCode || 'N/A' }}</span>
              </td>
              <td>v{{ r.latestVersion }}</td>
              <td>
                <span :class="r.isActive ? 'badge badge-green' : 'badge badge-rose'">
                  {{ r.isActive ? 'Active au runtime' : 'Retirée' }}
                </span>
              </td>
              <td style="display: flex; gap: 0.5rem;">
                <router-link :to="`/rules/${r.ruleId}`" class="btn btn-secondary btn-sm">
                  Détails & DMN
                </router-link>
                <button v-if="r.isActive && r.ruleId !== 'R001'" @click="retireRule(r.ruleId)" class="btn btn-danger btn-sm">
                  Retirer
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStockGapStore } from '../stores/stockGapStore';

const store = useStockGapStore();
const rules = ref([]);
const activeVersion = ref(1);

async function loadRules() {
  try {
    const res = await fetch('/api/rules');
    if (res.ok) {
      const data = await res.json();
      rules.value = data.rules;
      activeVersion.value = data.activeRuleSetVersion;
    }
  } catch (e) {
    console.error('Erreur chargement règles', e);
  }
}

async function retireRule(ruleId) {
  if (!confirm(`Confirmer le retrait de la règle ${ruleId} du référentiel actif ?`)) return;
  try {
    const res = await fetch(`/api/rules/${ruleId}/retirement`, { method: 'POST' });
    if (res.ok) {
      loadRules();
      store.fetchOverview();
    }
  } catch (e) {
    console.error('Erreur retrait règle', e);
  }
}

onMounted(() => {
  loadRules();
});
</script>
