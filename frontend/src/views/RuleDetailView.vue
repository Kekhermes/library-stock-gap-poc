<template>
  <div v-if="rule">
    <div style="display: flex; flex-wrap: wrap; gap: 1rem; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
      <div style="display: flex; flex-wrap: wrap; align-items: center; gap: 1rem;">
        <router-link to="/rules" class="btn btn-secondary btn-sm">← Retour aux règles</router-link>
        <h1 style="font-size: 1.3rem; font-weight: 700;">
          Règle : <span style="font-family: monospace; color: var(--accent-blue); overflow-wrap: anywhere;">{{ rule.ruleId }}</span> (v{{ rule.ruleVersion }})
        </h1>
      </div>

      <div style="display: flex; align-items: center; gap: 0.75rem;">
        <span :class="rule.active ? 'badge badge-green' : 'badge badge-rose'">
          {{ rule.active ? 'Active au runtime' : 'Retirée' }}
        </span>
        <button v-if="rule.active && rule.ruleId !== 'R001'" @click="retire" class="btn btn-danger btn-sm">
          Retirer la règle
        </button>
      </div>
    </div>

    <div style="display: grid; grid-template-columns: repeat(auto-fit,minmax(min(100%,22rem),1fr)); gap: 1.5rem; margin-bottom: 1.5rem;">
      <div class="card" style="margin-bottom: 0;">
        <div class="card-header">
          <span class="card-title">Métadonnées de la règle</span>
        </div>
        <div style="display: flex; flex-direction: column; gap: 0.5rem; font-size: 0.9rem;">
          <div><strong>Nom :</strong> {{ rule.name }}</div>
          <div><strong>Origine :</strong> <span class="badge badge-green">{{ rule.origin }}</span></div>
          <div><strong>Cause :</strong> <span class="badge badge-blue">{{ rule.causeCode }}</span></div>
          <div><strong>Namespace :</strong> <code>{{ rule.namespace }}</code></div>
          <div><strong>Décision DMN :</strong> <code>{{ rule.decisionName }}</code></div>
          <div><strong>Empreinte SHA-256 :</strong> <code style="font-size: 0.75rem;">{{ rule.artifactSha256 }}</code></div>
        </div>
      </div>

      <div class="card" style="margin-bottom: 0;">
        <div class="card-header">
          <span class="card-title">Portée & Limitations</span>
        </div>
        <div style="margin-bottom: 0.75rem;">
          <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); text-transform: uppercase;">Symptômes couverts :</div>
          <div style="margin-top: 0.25rem;">
            <span v-for="sk in rule.coveredSymptomKinds" :key="sk" class="badge badge-amber" style="margin-right: 0.25rem;">
              {{ sk }}
            </span>
          </div>
        </div>

        <div style="margin-bottom: 0.75rem;">
          <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); text-transform: uppercase;">Recommandations :</div>
          <ul style="padding-left: 1.25rem; font-size: 0.85rem; margin-top: 0.25rem;">
            <li v-for="(rec, i) in rule.recommendations" :key="i">{{ rec }}</li>
          </ul>
        </div>

        <div>
          <div style="font-size: 0.8rem; font-weight: 600; color: var(--text-muted); text-transform: uppercase;">Limitations explicites :</div>
          <ul style="padding-left: 1.25rem; font-size: 0.85rem; margin-top: 0.25rem; color: var(--text-muted);">
            <li v-for="(lim, i) in rule.limitations" :key="i">{{ lim }}</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- DMN Source Code -->
    <section style="border-top: 1px solid var(--border-color); padding: 1rem 0; margin-bottom: 1rem;">
      <h2 style="font-size: 1.1rem; margin-bottom: 0.75rem;">Table de décision et essai</h2>
      <label for="rule-test-case">Dossier</label>
      <select id="rule-test-case" v-model="selectedCase" @change="inspectRule" style="max-width: 100%; margin: 0.5rem;">
        <option value="">Sans essai</option>
        <option v-for="item in cases" :key="item.caseId" :value="item.caseId">{{ item.barcode }} · {{ item.title }}</option>
      </select>
      <span v-if="inspection?.match != null" :class="inspection.match ? 'badge badge-green' : 'badge badge-amber'">{{ inspection.inScope === false ? 'Hors portée' : inspection.match ? 'Règle applicable' : 'Non reconnue' }}</span>
      <p v-if="inspectionError" role="alert" style="color: var(--accent-rose);">{{ inspectionError }}</p>
      <div style="overflow-x: auto;">
        <table v-if="inspection" style="width: 100%; border-collapse: collapse; margin-top: 1rem; font-size: 0.85rem;">
          <thead><tr><th>Condition métier</th><th>Expression FEEL réelle</th><th>Valeur attendue</th><th>Résultat</th></tr></thead>
          <tbody><tr v-for="condition in inspection.conditions" :key="condition.expression">
            <td style="padding: 0.5rem;">{{ condition.question }}</td>
            <td style="padding: 0.5rem; max-width: 24rem; overflow-wrap: anywhere;"><code>{{ condition.expression }}</code><details><summary>Faits utilisés</summary><pre>{{ JSON.stringify(condition.facts,null,2) }}</pre></details></td>
            <td>{{ condition.expected }}</td>
            <td>{{ resultLabels[condition.result] }}</td>
          </tr></tbody>
        </table>
      </div>
    </section>
    <div class="card">
      <div class="card-header">
        <span class="card-title">Code source DMN / FEEL (Apache KIE 10.2.0)</span>
      </div>
      <pre class="code-block" style="max-height: 400px;">{{ rule.dmnXml }}</pre>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStockGapStore } from '../stores/stockGapStore';

const route = useRoute();
const store = useStockGapStore();
const rule = ref(null);
const cases = ref([]);
const selectedCase = ref('');
const inspection = ref(null);
const inspectionError = ref('');
const resultLabels = { TRUE: 'Vrai', FALSE: 'Faux', UNKNOWN: 'Inconnu', NOT_TESTED: 'Non testé' };

async function inspectRule() {
  inspectionError.value = '';
  try {
    const suffix = selectedCase.value ? `?caseId=${encodeURIComponent(selectedCase.value)}` : '';
    const response = await fetch(`/api/rules/${route.params.ruleId}/inspection${suffix}`);
    if (!response.ok) throw new Error('Essai de règle indisponible');
    inspection.value = await response.json();
  } catch (error) { inspectionError.value = error.message; }
}

async function loadRule() {
  const ruleId = route.params.ruleId;
  try {
    const res = await fetch(`/api/rules/${ruleId}`);
    if (res.ok) {
      const data = await res.json();
      rule.value = data.rule;
      const listing = await fetch('/api/cases?size=10');
      if (listing.ok) cases.value = (await listing.json()).items;
      await inspectRule();
    }
  } catch (e) {
    console.error('Erreur chargement règle', e);
  }
}

async function retire() {
  if (!confirm('Confirmer le retrait de la règle du référentiel ?')) return;
  try {
    const res = await fetch(`/api/rules/${rule.value.ruleId}/retirement`, { method: 'POST' });
    if (res.ok) {
      loadRule();
      store.fetchOverview();
    }
  } catch (e) {
    console.error('Erreur retrait', e);
  }
}

onMounted(() => {
  loadRule();
});
</script>
