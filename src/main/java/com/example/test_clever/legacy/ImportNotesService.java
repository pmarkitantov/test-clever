package com.example.test_clever.legacy;

import com.example.test_clever.legacy.dto.NoteDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ImportNotesService {
    private static final Logger log = LoggerFactory.getLogger(ImportNotesService.class);

    private final NamedParameterJdbcTemplate jdbc;
    private final LegacyStubService legacy;

    public ImportNotesService(NamedParameterJdbcTemplate jdbc, LegacyStubService legacy) {
        this.jdbc = jdbc;
        this.legacy = legacy;
    }

    private boolean existsNoteByLegacyGuid(String legacyGuid) {
        String sql = "SELECT 1 FROM patient_note WHERE legacy_guid = :g LIMIT 1";
        List<Integer> r = jdbc.query(sql, new MapSqlParameterSource("g", legacyGuid), (rs, rn) -> 1);
        return !r.isEmpty();
    }

    public ImportResult importAllFromStub() {
        List<NoteDto> notes = legacy.getNotes();
        int inserted = 0;
        int updated = 0;
        int skipped = 0;

        for (NoteDto n : notes) {
            try {
                Long patientId = findActivePatientIdByClientGuid(n.getClientGuid());
                if (patientId == null) {
                    skipped++;
                    continue;
                }

                Long userId = findOrCreateUserId(normalizeLogin(n.getLoggedUser()));

                String legacyGuid = deterministicLegacyGuid(n).toString();

                boolean existedBefore = existsNoteByLegacyGuid(legacyGuid);

                int affected = upsertNote(legacyGuid, patientId, userId, n);

                if (affected == 1) {
                    if (existedBefore) {
                        updated++;
                    } else {
                        inserted++;
                    }
                } else {
                    skipped++;
                }
            } catch (Exception ex) {
                skipped++;
                log.warn("[IMPORT WARN] note skipped: {}", ex.getMessage());
            }
        }
        return new ImportResult(inserted, updated, skipped);
    }

    private static String normalizeLogin(String login) {
        String v = (login == null || login.isBlank()) ? "system" : login.trim();
        return v;
    }

    private Long findActivePatientIdByClientGuid(String clientGuid) {
        if (clientGuid == null || clientGuid.isBlank()) return null;
        String sql = """
                SELECT id
                FROM patient_profile p
                WHERE p.status_id IN (200,210,230)
                  AND p.old_client_guid IS NOT NULL
                  AND EXISTS (
                    SELECT 1
                    FROM unnest(string_to_array(p.old_client_guid, ',')) AS g(raw)
                    WHERE trim(g.raw) = :guid
                  )
                LIMIT 1
                """;
        List<Long> ids = jdbc.query(sql,
                new MapSqlParameterSource("guid", clientGuid.trim()),
                (rs, rn) -> rs.getLong("id"));
        return ids.isEmpty() ? null : ids.get(0);
    }

    private Long findOrCreateUserId(String login) {
        String select = "SELECT id FROM company_user WHERE login = :login";
        List<Long> ids = jdbc.query(select, new MapSqlParameterSource("login", login), (rs, rn) -> rs.getLong("id"));
        if (!ids.isEmpty()) return ids.get(0);

        String insert = "INSERT INTO company_user (login) VALUES (:login) RETURNING id";
        return jdbc.queryForObject(insert, new MapSqlParameterSource("login", login), Long.class);
    }

    private int upsertNote(String legacyGuid, Long patientId, Long userId, NoteDto n) {

        Timestamp cdt = parseIsoToTs(n.getCreatedDateTime());
        Timestamp mdt = parseIsoToTs(n.getModifiedDateTime());

        String sql = """
                INSERT INTO patient_note
                  (legacy_guid, patient_id, note,
                   created_date_time, last_modified_date_time,
                   created_by_user_id, last_modified_by_user_id)
                VALUES
                  (:g, :pid, :note, :cdt, :mdt, :uid, :uid)
                ON CONFLICT (legacy_guid) DO UPDATE
                  SET note = EXCLUDED.note,
                      last_modified_date_time = EXCLUDED.last_modified_date_time,
                      last_modified_by_user_id = EXCLUDED.last_modified_by_user_id
                  WHERE EXCLUDED.last_modified_date_time > patient_note.last_modified_date_time
                """;

        MapSqlParameterSource p = new MapSqlParameterSource()
                .addValue("g", legacyGuid)
                .addValue("pid", patientId)
                .addValue("note", n.getComments())
                .addValue("cdt", cdt)
                .addValue("mdt", mdt)
                .addValue("uid", userId);

        return jdbc.update(sql, p);
    }

    private boolean wasJustInserted(String legacyGuid) {
        String sql = """
                SELECT created_date_time = last_modified_date_time AS just_inserted
                FROM patient_note
                WHERE legacy_guid = :g
                """;
        Boolean b = jdbc.queryForObject(sql, new MapSqlParameterSource("g", legacyGuid), Boolean.class);
        return Boolean.TRUE.equals(b);
    }

    private static Timestamp parseIsoToTs(String iso) {
        if (iso == null || iso.isBlank()) return new Timestamp(System.currentTimeMillis());
        try {
            return Timestamp.from(OffsetDateTime.parse(iso).toInstant());
        } catch (DateTimeParseException ex) {
            return new Timestamp(System.currentTimeMillis());
        }
    }

    private static UUID deterministicLegacyGuid(NoteDto n) {
        String base = (n.getClientGuid() == null ? "" : n.getClientGuid().trim())
                + "|" + (n.getCreatedDateTime() == null ? "" : n.getCreatedDateTime().trim())
                + "|" + (n.getComments() == null ? "" : n.getComments().trim());
        return UUID.nameUUIDFromBytes(base.getBytes());
    }

    public record ImportResult(int inserted, int updated, int skipped) {
        @Override
        public String toString() {
            return "ImportResult{inserted=%d, updated=%d, skipped=%d}".formatted(inserted, updated, skipped);
        }
    }
}