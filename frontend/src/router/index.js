import { createRouter, createWebHistory } from 'vue-router';
import OverviewView from '../views/OverviewView.vue';
import CasesView from '../views/CasesView.vue';
import CaseDossierView from '../views/CaseDossierView.vue';
import SystemView from '../views/SystemView.vue';

const routes = [
  { path: '/', name: 'Overview', component: OverviewView },
  { path: '/cases', name: 'Cases', component: CasesView },
  { path: '/cases/:caseId', name: 'CaseDossier', component: CaseDossierView },
  { path: '/system', name: 'System', component: SystemView }
];

export const router = createRouter({
  history: createWebHistory(),
  routes
});
