<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
      <h1 style="font-size: 1.5rem; font-weight: 700;">Dossiers d'écarts de stock</h1>
      <span style="color: var(--text-muted);">{{ totalCases }} cas trouvés</span>
    </div>

    <div class="card" style="padding: 1rem; margin-bottom: 1rem;">
      <div style="display: flex; gap: 1rem; align-items: center; flex-wrap: wrap;">
        <div>
          <label style="display: block; font-size: 0.75rem; color: var(--text-muted); margin-bottom: 0.25rem;">Site</label>
          <select v-model="filters.siteId" @change="loadCases">
            <option value="">Tous les sites</option>
            <option value="CENTRE">CENTRE</option>
            <option value="NORD">NORD</option>
            <option value="SUD">SUD</option>
          </select>
        </div>

        <div>
          <label style="display: block; font-size: 0.75rem; color: var(--text-muted); margin-bottom: 0.25rem;">Statut de workflow</label>
          <select v-model="filters.status" @change="loadCases">
            <option value="">Tous les statuts</option>
            <option value="A_ANALYSER">À analyser</option>
            <option value="EN_ANALYSE">En analyse</option>
            <option value="A_REVOIR">À revoir</option>
            <option value="DIAGNOSTIC_ACCEPTE">Diagnostic accepté</option>
          </select>
        </div>

        <div>
          <label style="display: block; font-size: 0.75rem; color: var(--text-muted); margin-bottom: 0.25rem;">Type de symptôme</label>
          <select v-model="filters.symptomKind" @change="loadCases">
            <option value="">Tous les symptômes</option>
            <option value="LOCATION_MISMATCH">LOCATION_MISMATCH</option>
            <option value="STATUS_MISMATCH">STATUS_MISMATCH</option>
            <option value="MISSING">MISSING</option>
            <option value="UNEXPECTED_PRESENCE">UNEXPECTED_PRESENCE</option>
            <option value="COUNT_UNCERTAIN">COUNT_UNCERTAIN</option>
          </select>
        </div>

        <div style="margin-left: auto;">
          <button @click="resetFilters" class="btn btn-secondary btn-sm" style="margin-top: 1.25rem;">
            Réinitialiser
          </button>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Code-barres</th>
              <th>Titre de l'exemplaire</th>
              <th>Site de rattachement</th>
              <th>Symptômes constatés</th>
              <th>Statut</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="c in cases" :key="c.caseId">
              <td>
                <router-link :to="`/cases/${c.caseId}`" style="font-family: monospace; font-weight: 600;">
                  {{ c.barcode }}
                </router-link>
              </td>
              <td>{{ c.title }}</td>
              <td><span class="badge badge-blue">{{ c.owningSiteId }}</span></td>
              <td>
                <span v-for="s in c.symptoms" :key="s.symptomId" class="badge badge-amber" style="margin-right: 0.25rem;">
                  {{ s.kind }}
                </span>
              </td>
              <td>
                <span :class="getStatusBadgeClass(c.status)">{{ formatStatus(c.status) }}</span>
                <span v-if="c.diagnosticConclusion" :class="getConclusionBadgeClass(c.diagnosticConclusion)" style="margin-left: 0.4rem;">
                  {{ formatConclusion(c.diagnosticConclusion, c.diagnosticOrigin) }}
                </span>
              </td>
              <td>
                <router-link :to="`/cases/${c.caseId}`" class="btn btn-secondary btn-sm">
                  Ouvrir dossier
                </router-link>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 1rem;">
        <button :disabled="page === 0" @click="prevPage" class="btn btn-secondary btn-sm">Précédent</button>
        <span style="color: var(--text-muted); font-size: 0.85rem;">Page {{ page + 1 }} sur {{ totalPages || 1 }}</span>
        <button :disabled="page + 1 >= totalPages" @click="nextPage" class="btn btn-secondary btn-sm">Suivant</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const cases = ref([]);
const totalCases = ref(0);
const totalPages = ref(1);
const page = ref(0);
const size = 20;

const filters = ref({
  siteId: '',
  status: '',
  symptomKind: ''
});

async function loadCases() {
  const params = new URLSearchParams({
    page: page.value,
    size: size
  });
  if (filters.value.siteId) params.append('siteId', filters.value.siteId);
  if (filters.value.status) params.append('status', filters.value.status);
  if (filters.value.symptomKind) params.append('symptomKind', filters.value.symptomKind);

  try {
    const res = await fetch(`/api/cases?${params.toString()}`);
    if (res.ok) {
      const data = await res.json();
      cases.value = data.items;
      totalCases.value = data.total;
      totalPages.value = data.totalPages;
    }
  } catch (e) {
    console.error('Erreur chargement cas', e);
  }
}

function prevPage() {
  if (page.value > 0) {
    page.value--;
    loadCases();
  }
}

function nextPage() {
  if (page.value + 1 < totalPages.value) {
    page.value++;
    loadCases();
  }
}

function resetFilters() {
  filters.value.siteId = '';
  filters.value.status = '';
  filters.value.symptomKind = '';
  page.value = 0;
  loadCases();
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

function getConclusionBadgeClass(conclusion) {
  if (conclusion === 'SUPPORTED') return 'badge badge-green';
  if (conclusion === 'PARTIAL') return 'badge badge-amber';
  return 'badge badge-rose';
}

function formatConclusion(conclusion, origin) {
  const labels = { SUPPORTED: 'Diagnostic étayé', PARTIAL: 'Diagnostic partiel', INDETERMINATE: 'Diagnostic indéterminé', CONFLICT: 'Diagnostic contradictoire' };
  return `${labels[conclusion] || conclusion}${origin ? ` · ${origin === 'DMN' ? 'DMN' : 'IA'}` : ''}`;
}

onMounted(() => {
  loadCases();
});
</script>
