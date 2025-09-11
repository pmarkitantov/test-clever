package com.example.test_clever.legacy;

import com.example.test_clever.legacy.dto.ClientDto;
import com.example.test_clever.legacy.dto.NoteDto;
import com.example.test_clever.legacy.dto.NotesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LegacyApiClient {

    private final WebClient legacyWebClient;
    public List<ClientDto> getAllClients() {
        ClientDto[] arr = legacyWebClient.post()
                .uri("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ClientDto[].class)
                .onErrorResume(ex -> Mono.error(new RuntimeException("Legacy /clients failed", ex)))
                .block();
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    /** POST /notes с телом запроса, возвращает список заметок по клиенту */
    public List<NoteDto> getNotes(String agency, String clientGuid, LocalDate from, LocalDate to) {
        NotesRequest payload = new NotesRequest(agency, from.toString(), to.toString(), clientGuid);
        NoteDto[] arr = legacyWebClient.post()
                .uri("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(NoteDto[].class)
                .onErrorResume(ex -> Mono.error(new RuntimeException("Legacy /notes failed", ex)))
                .block();
        return arr == null ? List.of() : Arrays.asList(arr);
    }
}