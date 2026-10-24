package com.library.stockgap.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.infrastructure.persistence.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class OutboxDispatcher {
    private static final Logger log = LoggerFactory.getLogger(OutboxDispatcher.class);

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String commandsTopic;
    private final boolean kafkaEnabled;

    public OutboxDispatcher(OutboxRepository outboxRepository,
                            KafkaTemplate<String, String> kafkaTemplate,
                            ObjectMapper objectMapper,
                            @Value("${stockgap.topics.commands:stock-gap.agent.commands.v1}") String commandsTopic,
                            @Value("${stockgap.kafka-enabled:true}") boolean kafkaEnabled) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.commandsTopic = commandsTopic;
        this.kafkaEnabled = kafkaEnabled;
    }

    @Scheduled(fixedDelay = 500)
    public void dispatchPending() {
        if (!kafkaEnabled) return;

        try {
            List<Map<String, Object>> pending = outboxRepository.fetchPendingEvents(10);
            for (Map<String, Object> event : pending) {
                UUID eventId = (UUID) event.get("eventId");
                UUID epoch = (UUID) event.get("datasetEpoch");
                UUID missionId = (UUID) event.get("missionId");
                Map<String, Object> env = (Map<String, Object>) event.get("envelope");

                String payload = objectMapper.writeValueAsString(env);
                kafkaTemplate.send(commandsTopic, missionId.toString(), payload)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            outboxRepository.markPublished(epoch, eventId);
                            log.debug("Commande Kafka envoyée avec succès: missionId={}, eventId={}", missionId, eventId);
                        } else {
                            log.warn("Échec d'envoi Kafka pour eventId={}: {}", eventId, ex.getMessage());
                        }
                    });
            }
        } catch (Exception e) {
            log.debug("Cycle de dispatch outbox: {}", e.getMessage());
        }
    }
}
