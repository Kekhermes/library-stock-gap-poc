<template>
  <div>
    <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 1.25rem;">État du système & Réinitialisation</h1>

    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; margin-bottom: 1.5rem;">
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
            <span style="color: var(--accent-purple); font-weight: 600;">Agents SDK / Confluent-Kafka</span>
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
            <code style="display: block; background: #090d16; padding: 0.5rem; border-radius: 4px; font-size: 0.85rem; margin-top: 0.25rem;">
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

    <!-- Reset Box -->
    <div class="card" style="border: 1px solid var(--accent-rose); background: rgba(244, 63, 94, 0.05);">
      <div class="card-header">
        <span class="card-title" style="color: var(--accent-rose);">Zone d'administration — Réinitialisation complète</span>
        <span class="badge badge-rose">Action destructrice</span>
      </div>

      <p style="font-size: 0.9rem; margin-bottom: 1rem; color: var(--text-main);">
        La réinitialisation recrée une époque vierge, recharge les 1 200 exemplaires et 120 cas du jeu <code>demo</code>, réactive uniquement la règle initiale <code>R001 v1</code>, et archive proprement les anciennes analyses sans écraser votre clé API locale ni votre configuration.
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
import { ref } from 'vue';
import { useStockGapStore } from '../stores/stockGapStore';

const store = useStockGapStore();
const resetting = ref(false);

async function handleReset() {
  if (!confirm("Voulez-vous réinitialiser le POC ? Tous les cas reviendront à l'état initial avec la seule règle R001 active.")) {
    return;
  }

  resetting.value = true;
  try {
    const res = await fetch('/api/system/reset', { method: 'POST' });
    if (res.ok) {
      await store.fetchOverview();
      await store.fetchSyncSnapshot();
      alert("Système réinitialisé avec succès avec une nouvelle époque !");
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
