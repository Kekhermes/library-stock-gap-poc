<template>
  <div>
    <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 1.25rem;">Vue d'ensemble opérationnelle</h1>

    <div style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 1rem; margin-bottom: 1.5rem;" v-if="store.overview">
      <div class="card" style="margin-bottom: 0;">
        <div style="color: var(--text-muted); font-size: 0.85rem;">Total des cas d'écart</div>
        <div style="font-size: 1.8rem; font-weight: 700; color: var(--accent-blue);">{{ store.overview.totalCases }}</div>
        <div style="font-size: 0.8rem; color: var(--text-muted); margin-top: 0.25rem;">Campagne d'inventaire</div>
      </div>

      <div class="card" style="margin-bottom: 0;">
        <div style="color: var(--text-muted); font-size: 0.85rem;">À analyser</div>
        <div style="font-size: 1.8rem; font-weight: 700; color: var(--accent-amber);">{{ store.overview.pendingCases }}</div>
        <div style="font-size: 0.8rem; color: var(--text-muted); margin-top: 0.25rem;">Non encore soumis au diagnostic</div>
      </div>

      <div class="card" style="margin-bottom: 0;">
        <div style="color: var(--text-muted); font-size: 0.85rem;">Diagnostics à revoir</div>
        <div style="font-size: 1.8rem; font-weight: 700; color: var(--accent-purple);">{{ store.overview.waitingReview }}</div>
        <div style="font-size: 0.8rem; color: var(--text-muted); margin-top: 0.25rem;">En attente de décision Admin</div>
      </div>

      <div class="card" style="margin-bottom: 0;">
        <div style="color: var(--text-muted); font-size: 0.85rem;">Diagnostics acceptés</div>
        <div style="font-size: 1.8rem; font-weight: 700; color: var(--accent-green);">{{ store.overview.acceptedDiagnostics }}</div>
        <div style="font-size: 0.8rem; color: var(--text-muted); margin-top: 0.25rem;">Validés par l'expert</div>
      </div>
    </div>

    <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 1.5rem;">
      <div class="card">
        <div class="card-header">
          <span class="card-title">Règles actives du référentiel (v{{ store.activeRuleSetVersion }})</span>
          <router-link to="/rules" class="btn btn-secondary btn-sm">Consulter le catalogue</router-link>
        </div>

        <div v-if="store.activeRules.length === 0" style="color: var(--text-muted); font-style: italic;">
          Aucune règle active chargée.
        </div>

        <div class="table-container" v-else>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Cause diagnostiquée</th>
                <th>Symptômes couverts</th>
                <th>Origine</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="r in store.activeRules" :key="r.ruleId">
                <td><router-link :to="`/rules/${r.ruleId}`" style="font-family: monospace; font-weight: 600;">{{ r.ruleId }}</router-link></td>
                <td>{{ r.name }}</td>
                <td><span class="badge badge-blue">{{ r.causeCode }}</span></td>
                <td>
                  <span v-for="sk in r.coveredSymptomKinds" :key="sk" class="badge badge-amber" style="margin-right: 0.25rem;">
                    {{ sk }}
                  </span>
                </td>
                <td><span class="badge badge-green">{{ r.origin }}</span></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <span class="card-title">Événements en direct (SSE)</span>
          <span class="badge badge-green" v-if="store.connected">Connecté</span>
        </div>

        <div v-if="store.recentNotifications.length === 0" style="color: var(--text-muted); font-style: italic; font-size: 0.85rem;">
          En attente de notifications du backend...
        </div>

        <div style="display: flex; flex-direction: column; gap: 0.5rem; max-height: 380px; overflow-y: auto;">
          <div v-for="(n, idx) in store.recentNotifications" :key="idx"
               style="background: rgba(255,255,255,0.02); padding: 0.6rem; border-radius: 4px; border: 1px solid var(--border-color); font-size: 0.8rem;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.2rem;">
              <span class="badge badge-purple">{{ n.type }}</span>
              <span style="color: var(--text-muted); font-size: 0.75rem;">{{ n.time }}</span>
            </div>
            <div style="color: var(--text-muted); font-family: monospace; word-break: break-all;">
              {{ JSON.stringify(n.data.data || n.data) }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useStockGapStore } from '../stores/stockGapStore';

const store = useStockGapStore();
</script>
