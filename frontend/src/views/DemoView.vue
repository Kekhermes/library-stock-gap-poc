<template>
  <div v-if="demo">
    <header class="demo-header"><h1>Démo guidée</h1><router-link class="btn btn-secondary btn-sm" to="/system">Préparation et remise à zéro</router-link></header>
    <div class="demo-summary"><span>10 dossiers · 2 + 4 + 4</span><span>Référentiel v{{ demo.ruleSetVersion }}</span><span>{{ demo.rules.filter(rule => rule.isActive).length }} règles actives</span></div>
    <section v-for="key in order" :key="key" class="demo-band">
      <h2>{{ labels[key] }}</h2>
      <div class="demo-progress"><span>{{ items(key).filter(item => item.diagnosticOrigin === 'LLM').length }} diagnostic LLM</span><span>{{ items(key).filter(item => item.rulePublished).length }} publication issue d'un dossier</span><span>{{ items(key).filter(item => item.diagnosticOrigin === 'DMN').length }} diagnostics DMN</span></div>
      <table><thead><tr><th>Exemplaire</th><th>Titre</th><th>État</th><th>Origine du diagnostic</th><th></th></tr></thead><tbody>
        <tr v-for="item in items(key)" :key="item.caseId"><td><code>{{ item.barcode }}</code></td><td>{{ item.title }}</td><td>{{ states[item.status] || item.status }}</td><td>{{ item.diagnosticOrigin || 'Non analysé' }}</td><td><router-link :to="`/cases/${item.caseId}`">Ouvrir le dossier</router-link></td></tr>
      </tbody></table>
    </section>
    <section class="demo-band"><h2>Règles du référentiel</h2><div v-for="rule in demo.rules" :key="rule.ruleId" class="demo-rule"><router-link :to="`/rules/${rule.ruleId}`">{{ rule.details?.name || rule.ruleId }} · v{{ rule.latestVersion }}</router-link><span :class="rule.isActive ? 'badge badge-green' : 'badge badge-rose'">{{ rule.isActive ? 'Active' : 'Retirée' }}</span></div></section>
  </div>
  <p v-else-if="error" role="alert">{{ error }}</p>
  <p v-else>Chargement...</p>
</template>
<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
const demo = ref(null), error = ref('');
const order = ['LOCATION_MISMATCH', 'ReturnReceived', 'RepairStarted'];
const labels = { LOCATION_MISMATCH: '1. Localisations déjà connues', ReturnReceived: '2. Retour non répercuté : acquérir une règle', RepairStarted: '3. Réparation non répercutée : seconde acquisition' };
const states = { A_ANALYSER: 'À analyser', EN_ANALYSE: 'En cours', A_REVOIR: 'Diagnostic à revoir', DIAGNOSTIC_ACCEPTE: 'Diagnostic accepté' };
let interval;
function items(key) { return demo.value?.groups[key] || []; }
async function load() { try { const response = await fetch('/api/demo'); if (!response.ok) throw new Error('Démo indisponible'); demo.value = await response.json(); } catch (failure) { error.value = failure.message; } }
onMounted(() => { load(); interval = setInterval(load, 3000); });
onUnmounted(() => clearInterval(interval));
</script>
<style scoped>
.demo-header,.demo-summary,.demo-progress,.demo-rule {display:flex;flex-wrap:wrap;align-items:center;gap:1rem}
.demo-header {justify-content:space-between;margin-bottom:1rem}.demo-header h1{font-size:1.5rem}
.demo-summary,.demo-progress{color:var(--text-muted);font-size:.85rem}.demo-summary{margin-bottom:1rem}
.demo-band{padding:1rem 0;border-top:1px solid var(--border-color)}.demo-band h2{font-size:1.1rem;margin-bottom:.75rem}
table{width:100%;border-collapse:collapse;margin-top:.75rem;font-size:.85rem}th,td{padding:.6rem;text-align:left;border-bottom:1px solid var(--border-color);overflow-wrap:anywhere}
.demo-rule{padding:.5rem 0}
@media(max-width:760px){table{table-layout:fixed}th:nth-child(2),td:nth-child(2){display:none}th,td{padding:.4rem;font-size:.75rem}code{overflow-wrap:anywhere}}
</style>