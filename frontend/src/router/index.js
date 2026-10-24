import { createRouter, createWebHistory } from 'vue-router';
import OverviewView from '../views/OverviewView.vue';
import CasesView from '../views/CasesView.vue';
import CaseDossierView from '../views/CaseDossierView.vue';
import RulesView from '../views/RulesView.vue';
import RuleDetailView from '../views/RuleDetailView.vue';
import SystemView from '../views/SystemView.vue';

const routes = [
  { path: '/', name: 'Overview', component: OverviewView },
  { path: '/cases', name: 'Cases', component: CasesView },
  { path: '/cases/:caseId', name: 'CaseDossier', component: CaseDossierView },
  { path: '/rules', name: 'Rules', component: RulesView },
  { path: '/rules/:ruleId', name: 'RuleDetail', component: RuleDetailView },
  { path: '/system', name: 'System', component: SystemView }
];

export const router = createRouter({ history: createWebHistory(), routes });
