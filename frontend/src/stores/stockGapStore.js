import { defineStore } from 'pinia';

export const useStockGapStore = defineStore('stockGap', {
  state: () => ({
    overview: null,
    activeRules: [],
    activeRuleSetVersion: 1,
    cursor: null,
    connected: false,
    eventSource: null,
    recentNotifications: [],
    loading: false
  }),

  actions: {
    async fetchOverview() {
      try {
        const res = await fetch('/api/overview');
        if (res.ok) {
          const data = await res.json();
          this.overview = data;
          this.activeRules = data.activeRules || [];
          this.activeRuleSetVersion = data.activeRuleSetVersion || 1;
        }
      } catch (e) {
        console.error('Erreur chargement overview', e);
      }
    },

    async fetchSyncSnapshot() {
      try {
        const res = await fetch('/api/sync-snapshot');
        if (res.ok) {
          const data = await res.json();
          this.cursor = data.cursor;
          this.activeRuleSetVersion = data.activeRuleSetVersion;
          this.activeRules = data.activeRules || [];
        }
      } catch (e) {
        console.error('Erreur sync snapshot', e);
      }
    },

    connectSse() {
      if (this.eventSource) {
        this.eventSource.close();
      }

      const url = this.cursor ? `/api/events?after=${encodeURIComponent(this.cursor)}` : '/api/events';
      const es = new EventSource(url);
      this.eventSource = es;

      es.onopen = () => {
        this.connected = true;
      };

      es.onerror = () => {
        this.connected = false;
        es.close();
        setTimeout(() => this.connectSse(), 3000);
      };

      const eventTypes = [
        'AnalysisChanged', 'MissionChanged', 'DiagnosticReady',
        'ProposalChanged', 'ValidationCompleted', 'PublicationActivated',
        'ConversationMessageChanged', 'DatasetReset'
      ];

      eventTypes.forEach(type => {
        es.addEventListener(type, (evt) => {
          try {
            const data = JSON.parse(evt.data);
            if (data.cursor) {
              this.cursor = data.cursor;
            }
            this.recentNotifications.unshift({
              type,
              time: new Date().toLocaleTimeString(),
              data
            });
            if (this.recentNotifications.length > 30) {
              this.recentNotifications.pop();
            }

            // Refresh overview on major events
            if (['AnalysisChanged', 'PublicationActivated', 'DatasetReset'].includes(type)) {
              this.fetchOverview();
            }
          } catch (e) {
            console.error('Erreur lecture SSE event', e);
          }
        });
      });
    }
  }
});
