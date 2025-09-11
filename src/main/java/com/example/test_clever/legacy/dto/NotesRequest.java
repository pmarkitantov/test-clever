package com.example.test_clever.legacy.dto;

public record NotesRequest(
        String agency,
        String dateFrom,
        String dateTo,
        String clientGuid
) {
}