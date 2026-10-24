package com.library.stockgap.web;

import com.library.stockgap.domain.service.SseHubService;
import com.library.stockgap.infrastructure.persistence.ActiveDatasetRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SseController {

    private final SseHubService sseHubService;
    private final ActiveDatasetRepository activeDatasetRepository;

    public SseController(SseHubService sseHubService, ActiveDatasetRepository activeDatasetRepository) {
        this.sseHubService = sseHubService;
        this.activeDatasetRepository = activeDatasetRepository;
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestParam(required = false) String after,
            @RequestHeader(name = "Last-Event-ID", required = false) String lastEventId) {
        UUID epoch = activeDatasetRepository.requireActiveEpoch();
        String cursor = lastEventId != null ? lastEventId : after;
        return sseHubService.subscribe(epoch, cursor);
    }

    @GetMapping("/sync-snapshot")
    public ResponseEntity<Map<String, Object>> getSyncSnapshot() {
        UUID epoch = activeDatasetRepository.requireActiveEpoch();
        return ResponseEntity.ok(sseHubService.getSyncSnapshot(epoch));
    }
}
