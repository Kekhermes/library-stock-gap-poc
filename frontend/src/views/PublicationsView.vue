<template>
  <div>
    <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 1.25rem;">Historique des publications du référentiel</h1>

    <div class="card">
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Version</th>
              <th>Type de publication</th>
              <th>Empreinte SHA-256 Manifeste</th>
              <th>Approuvé par</th>
              <th>Date de scellement</th>
              <th>Statut</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="pub in publications" :key="pub.ruleSetVersion">
              <td><strong>v{{ pub.ruleSetVersion }}</strong></td>
              <td>
                <span :class="getKindBadge(pub.kind)">{{ pub.kind }}</span>
              </td>
              <td><code style="font-size: 0.75rem;">{{ pub.manifestSha256 }}</code></td>
              <td>{{ pub.approvedBy }}</td>
              <td>{{ formatDate(pub.sealedAt || pub.createdAt) }}</td>
              <td>
                <span :class="pub.isActive ? 'badge badge-green' : 'badge badge-secondary'">
                  {{ pub.isActive ? 'Active' : 'Archivée' }}
                </span>
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

const publications = ref([]);

async function loadPublications() {
  try {
    const res = await fetch('/api/publications');
    if (res.ok) {
      publications.value = await res.json();
    }
  } catch (e) {
    console.error('Erreur chargement publications', e);
  }
}

function getKindBadge(kind) {
  switch (kind) {
    case 'BOOTSTRAP': return 'badge badge-blue';
    case 'AGENT_CHANGE': return 'badge badge-purple';
    case 'ADMIN_RETIRE': return 'badge badge-rose';
    default: return 'badge badge-secondary';
  }
}

function formatDate(iso) {
  if (!iso) return '';
  return new Date(iso).toLocaleDateString('fr-FR', {
    hour: '2-digit', minute: '2-digit', second: '2-digit'
  });
}

onMounted(() => {
  loadPublications();
});
</script>
