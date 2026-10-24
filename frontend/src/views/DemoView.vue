<template>
  <div>
    <div style="margin-bottom: 1.5rem;">
      <h1 style="font-size: 1.5rem; font-weight: 700;">Démonstration guidée de la boucle hybride</h1>
      <p style="color: var(--text-muted); font-size: 0.9rem; margin-top: 0.25rem;">
        Parcours pédagogique en 6 étapes illustrant l'acquisition de connaissances contrôlée (DMN 1.5 + Agents IA).
      </p>
    </div>

    <!-- Stepper Navigation -->
    <div style="display: grid; grid-template-columns: repeat(6, 1fr); gap: 0.5rem; margin-bottom: 1.5rem;">
      <div v-for="(step, idx) in steps" :key="idx"
           :style="{
             padding: '0.75rem',
             borderRadius: '6px',
             border: '1px solid var(--border-color)',
             background: currentStep === idx ? 'rgba(56, 189, 248, 0.15)' : 'var(--bg-secondary)',
             borderLeft: currentStep === idx ? '4px solid var(--accent-blue)' : '1px solid var(--border-color)',
             cursor: 'pointer'
           }"
           @click="currentStep = idx">
        <div style="font-size: 0.75rem; color: var(--text-muted);">Étape {{ idx + 1 }}</div>
        <div style="font-weight: 600; font-size: 0.85rem; margin-top: 0.2rem;">{{ step.title }}</div>
      </div>
    </div>

    <!-- Active Step Content -->
    <div class="card">
      <div class="card-header">
        <div>
          <span class="badge badge-blue" style="margin-right: 0.5rem;">Étape {{ currentStep + 1 }} / 6</span>
          <span class="card-title">{{ steps[currentStep].title }}</span>
        </div>
        <span class="badge badge-purple">{{ steps[currentStep].mechanism }}</span>
      </div>

      <div style="font-size: 0.95rem; margin-bottom: 1.25rem; line-height: 1.6;">
        {{ steps[currentStep].description }}
      </div>

      <!-- Action Panel -->
      <div style="background: rgba(0,0,0,0.25); border: 1px solid var(--border-color); border-radius: 6px; padding: 1.25rem; margin-bottom: 1.5rem;">
        <div style="font-weight: 600; margin-bottom: 0.5rem;">Démonstration concrète :</div>
        <div style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 1rem;">
          {{ steps[currentStep].actionText }}
        </div>

        <div style="display: flex; gap: 1rem; align-items: center;">
          <router-link v-if="steps[currentStep].link" :to="steps[currentStep].link" class="btn btn-primary btn-sm">
            {{ steps[currentStep].btnText }} →
          </router-link>
          <button v-if="currentStep < 5" @click="currentStep++" class="btn btn-secondary btn-sm">
            Étape suivante
          </button>
        </div>
      </div>

      <!-- Pedagogy Box -->
      <div style="border-left: 3px solid var(--accent-green); background: rgba(74, 222, 128, 0.05); padding: 0.75rem 1rem; font-size: 0.85rem;">
        <strong>Principe architectural prouvé :</strong> {{ steps[currentStep].principle }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const currentStep = ref(0);

const steps = [
  {
    title: "1. Diagnostic déterministe immédiat",
    mechanism: "DMN R001 v1",
    description: "Un cas d'écart portant uniquement sur un déplacement d'emplacement sur le même site (LOCATION_MISMATCH) est évalué par la règle bootstrap R001. Le moteur Apache KIE conclut immédiatement sans appel LLM ni consommation de jetons.",
    actionText: "Ouvrez le premier cas de la liste pour constater le diagnostic SUPPORTED immédiat.",
    btnText: "Voir les cas d'écart",
    link: "/cases",
    principle: "Le déterminisme prime toujours : ce qui est déjà formalisé ne doit jamais coûter de jetons LLM."
  },
  {
    title: "2. Détection de couverture partielle & Fallback",
    mechanism: "Kafka Outbox -> InvestigationRequested",
    description: "Sur un cas complexe présentant à la fois un écart d'emplacement et un écart de statut (STATUS_MISMATCH), la règle R001 ne couvre pas tout. Le moteur Java constate la couverture partielle et émet une commande Kafka d'investigation.",
    actionText: "L'investigateur IA analyse les preuves du snapshot (prêts, retours, transferts) sans jamais accéder à l'oracle.",
    btnText: "Examiner un dossier en cours",
    link: "/cases",
    principle: "Une couverture partielle ne bloque pas le système mais motive un repli contrôlé et auditable."
  },
  {
    title: "3. Proposition de diagnostic & revue humaine",
    mechanism: "REST Admin -> Acceptation",
    description: "L'investigateur propose une explication étayée (ex: prêt ouvert non projeté dans le stock). Le diagnostic arrive dans l'interface pour revue par l'Admin. L'Admin accepte explicitement le diagnostic.",
    actionText: "L'acceptation d'un diagnostic est un acte métier distinct de la création d'une règle.",
    btnText: "Consulter les dossiers",
    link: "/cases",
    principle: "Séparation stricte : valider une vérité factuelle ponctuelle ne vaut pas approbation d'une règle générale."
  },
  {
    title: "4. Synthèse agentique de règle DMN",
    mechanism: "RuleSynthesisRequested -> RuleChangeProposed",
    description: "L'acceptation du diagnostic déclenche automatiquement l'agent de règles. Celui-ci formalise une règle DMN générale (ex: R002 sur la corrélation des prêts), vérifie sa conformité FEEL et la soumet à prévalidation Java.",
    actionText: "Une nouvelle proposition apparaît dans la file avec son diff et son modèle DMN 1.5.",
    btnText: "Voir les propositions de règles",
    link: "/proposals",
    principle: "Monopole de l'agent : l'humain n'écrit pas de DMN à la main pour éviter les régressions non typées."
  },
  {
    title: "5. Validation & Publication atomique",
    mechanism: "ValidationReport -> Transaction PostgreSQL",
    description: "Le backend rejoue la règle sur les cas de test. Si le rapport est validé (PASSED), l'Admin clique sur 'Approuver et Publier'. Une nouvelle version du référentiel (v2) est scellée et activée sans redémarrage.",
    actionText: "La publication est atomique. Les analyses déjà en cours conservent leur référentiel initial v1.",
    btnText: "Consulter l'historique des publications",
    link: "/publications",
    principle: "Publication à chaud et immutabilité : aucune activation partielle ni réécriture de l'historique."
  },
  {
    title: "6. Clôture de la boucle : Résolution déterministe",
    mechanism: "DMN R002 v1 au Runtime",
    description: "Tout nouveau cas présentant le même problème de prêt est désormais analysé par le moteur DMN déterministe grâce à la règle R002 active. La boucle d'apprentissage est bouclée avec zéro appel LLM.",
    actionText: "Le cas qui nécessitait auparavant une investigation IA est maintenant résolu instantanément.",
    btnText: "Retourner à la vue d'ensemble",
    link: "/",
    principle: "L'apprentissage continu contrôlé réduit durablement le recours aux modèles payants."
  }
];
</script>
