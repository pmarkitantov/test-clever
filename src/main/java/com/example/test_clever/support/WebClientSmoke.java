package com.example.test_clever.support;

import com.example.test_clever.config.LegacyApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientSmoke implements CommandLineRunner {

    private final WebClient legacyWebClient;
    private final LegacyApiProperties props;

    @Override
    public void run(String... args) {
        log.info("[SMOKE] legacy.base-url = {}", props.baseUrl());
    }
}