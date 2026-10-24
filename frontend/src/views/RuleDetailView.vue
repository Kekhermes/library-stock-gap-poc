<template>
  <div v-if="rule">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
      <div style="display: flex; align-items: center; gap: 1rem;">
        <router-link to="/rules" class="btn btn-secondary btn-sm">← Retour aux règles</router-link>
        <h1 style="font-size: 1.3rem; font-weight: 700;">
          Règle : <span style="font-family: monospace; color: var(--accent-blue);">{{ rule.ruleId }}</span> (v{{ rule.ruleVersion }})
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

    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; margin-bottom: 1.5rem;">
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

async function loadRule() {
  const ruleId = route.params.ruleId;
  try {
    const res = await fetch(`/api/rules/${ruleId}`);
    if (res.ok) {
      const data = await res.json();
      rule.value = data.rule;
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
