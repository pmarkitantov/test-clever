package com.example.test_clever.scheduler;

import com.example.test_clever.legacy.ImportNotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ImportScheduler {

    private static final Logger log = LoggerFactory.getLogger(ImportScheduler.class);
    private final ImportNotesService importNotesService;

    public ImportScheduler(ImportNotesService importNotesService) {
        this.importNotesService = importNotesService;
    }

    @Scheduled(fixedRate = 10_000, zone = "Europe/Warsaw")
    public void runImport() {
        log.info("[SCHEDULED] Import started");
        var result = importNotesService.importAllFromStub();
        log.info("[SCHEDULED] Import finished: {}", result);
    }
}