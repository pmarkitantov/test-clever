package com.example.test_clever.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "legacy.api")

public record LegacyApiProperties(
        String baseUrl,
        int connectTimeoutMs,
        int readTimeoutMs
) {
}