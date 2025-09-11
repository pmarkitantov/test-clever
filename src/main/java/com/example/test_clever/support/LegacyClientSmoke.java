package com.example.test_clever.support;

import com.example.test_clever.legacy.LegacyApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegacyClientSmoke implements CommandLineRunner {

    private final LegacyApiClient client;

    @Override
    public void run(String... args) {
        try {
            var clients = client.getAllClients();
            log.info("[SMOKE] Legacy /clients returned {} items", clients.size());
        } catch (Exception ex) {
            log.warn("[SMOKE] Legacy /clients call failed: {}", ex.getMessage());
        }
    }
}