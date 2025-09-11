package com.example.test_clever.repo;

import com.example.test_clever.domain.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
    Optional<PatientNote> findByLegacyGuid(String legacyGuid);
}
