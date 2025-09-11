package com.example.test_clever.legacy;

import com.example.test_clever.legacy.dto.ClientDto;
import com.example.test_clever.legacy.dto.NoteDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LegacyStubService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ClientDto> getClients() {
        return readJsonArray("legacy/clients.json", new TypeReference<List<ClientDto>>() {
        });
    }

    public List<NoteDto> getNotes() {
        return readJsonArray("legacy/notes.json", new TypeReference<List<NoteDto>>() {
        });
    }

    public List<NoteDto> getNotesByClientGuid(String clientGuid) {
        if (clientGuid == null) return Collections.emptyList();
        return getNotes().stream()
                .filter(n -> clientGuid.equals(n.getClientGuid()))
                .collect(Collectors.toList());
    }

    private <T> T readJsonArray(String path, TypeReference<T> typeRef) {
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStream is = resource.getInputStream()) {
            return objectMapper.readValue(is, typeRef);
        } catch (IOException e) {
            System.err.println("Failed to read " + path + ": " + e.getMessage());
            try {
                return (T) Collections.emptyList();
            } catch (ClassCastException cce) {
                throw new RuntimeException("Unexpected type for empty fallback", cce);
            }
        }
    }
}
