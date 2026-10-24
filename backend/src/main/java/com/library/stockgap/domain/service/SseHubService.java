package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.SseNotification;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import com.library.stockgap.infrastructure.persistence.SseStreamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseHubService {
    private static final Logger log = LoggerFactory.getLogger(SseHubService.class);

    private final SseStreamRepository sseStreamRepository;
    private final CaseRepository caseRepository;
    private final RuleRepository ruleRepository;
    private final List<SseEmitter> activeEmitters = new CopyOnWriteArrayList<>();

    public SseHubService(SseStreamRepository sseStreamRepository,
                         CaseRepository caseRepository,
                         RuleRepository ruleRepository) {
        this.sseStreamRepository = sseStreamRepository;
        this.caseRepository = caseRepository;
        this.ruleRepository = ruleRepository;
    }

    public SseEmitter subscribe(UUID epoch, String afterCursor) {
        SseEmitter emitter = new SseEmitter(180_000L); // 3 minutes timeout
        activeEmitters.add(emitter);

        emitter.onCompletion(() -> activeEmitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            activeEmitters.remove(emitter);
        });
        emitter.onError(e -> {
            activeEmitters.remove(emitter);
        });

        // Replay missed events if afterCursor provided
        if (afterCursor != null && !afterCursor.isBlank()) {
            try {
                String[] parts = afterCursor.split(":");
                if (parts.length == 2) {
                    long afterPos = Long.parseLong(parts[1]);
                    List<SseNotification> missed = sseStreamRepository.getEventsAfter(epoch, afterPos, 100);
                    for (SseNotification notif : missed) {
                        sendNotificationToEmitter(emitter, notif);
                    }
                }
            } catch (Exception e) {
                log.warn("Erreur replay curseur SSE: {}", e.getMessage());
            }
        }

        return emitter;
    }

    public void publishEvent(UUID epoch, String eventType, String aggregateType, String aggregateId, Map<String, Object> data) {
        SseNotification notification = sseStreamRepository.publishEvent(epoch, eventType, aggregateType, aggregateId, data);
        broadcast(notification);
    }

    private void broadcast(SseNotification notification) {
        List<SseEmitter> dead = new ArrayList<>();
        for (SseEmitter emitter : activeEmitters) {
            try {
                sendNotificationToEmitter(emitter, notification);
            } catch (Exception e) {
                dead.add(emitter);
            }
        }
        activeEmitters.removeAll(dead);
    }

    private void sendNotificationToEmitter(SseEmitter emitter, SseNotification notif) throws IOException {
        emitter.send(SseEmitter.event()
            .id(notif.cursor())
            .name(notif.eventType())
            .data(notif, MediaType.APPLICATION_JSON));
    }

    public Map<String, Object> getSyncSnapshot(UUID epoch) {
        String cursor = sseStreamRepository.getCurrentCursor(epoch);
        int activeVer = ruleRepository.getActiveRuleSetVersion(epoch);
        var activeRules = ruleRepository.getActiveRules(epoch);
        var cases = caseRepository.findCases(epoch, null, null, null, 0, 50);

        return Map.of(
            "datasetEpoch", epoch,
            "cursor", cursor,
            "activeRuleSetVersion", activeVer,
            "activeRules", activeRules,
            "casesCount", caseRepository.countCases(epoch, null, null, null),
            "recentCases", cases
        );
    }
}
