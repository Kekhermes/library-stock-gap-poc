<template>
  <div class="app-container">
    <aside class="sidebar">
      <div class="sidebar-brand">
        <span style="font-size: 1.3rem;">📚</span>
        <span>Stock Gap POC</span>
      </div>

      <nav class="sidebar-nav">
        <router-link to="/" class="nav-item"><span>📊</span> Vue d'ensemble</router-link>
        <router-link to="/cases" class="nav-item"><span>📂</span> Dossiers d'écarts</router-link>
        <router-link to="/system" class="nav-item"><span>⚙️</span> Système & Reset</router-link>
      </nav>

      <div class="sidebar-footer">
        <div>Investigation : <strong>GPT agentique</strong></div>
        <div style="margin-top: 0.25rem;">Données : <strong>snapshot figé</strong></div>
      </div>
    </aside>

    <div class="main-content">
      <header class="top-bar">
        <div style="display: flex; align-items: center; gap: 0.75rem;">
          <span style="font-weight: 600;">POC — Écarts de Stock de Bibliothèque</span>
          <span class="badge badge-blue">GPT Agent</span>
        </div>
        <div style="display: flex; align-items: center; gap: 1rem;">
          <div style="display: flex; align-items: center; gap: 0.4rem; font-size: 0.85rem;">
            <span :style="{ display: 'inline-block', width: '8px', height: '8px', borderRadius: '50%', backgroundColor: store.connected ? '#4ade80' : '#f43f5e' }"></span>
            <span style="color: var(--text-muted);">{{ store.connected ? 'SSE Connecté' : 'SSE Déconnecté' }}</span>
          </div>
          <div v-if="store.cursor" style="font-size: 0.75rem; color: var(--text-muted); font-family: monospace;">Curseur: {{ store.cursor.split(':')[1] || '0' }}</div>
        </div>
      </header>
      <main class="page-body"><router-view /></main>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useStockGapStore } from './stores/stockGapStore';
const store = useStockGapStore();
onMounted(async () => {
  await store.fetchOverview();
  await store.fetchSyncSnapshot();
  store.connectSse();
});
</script>
