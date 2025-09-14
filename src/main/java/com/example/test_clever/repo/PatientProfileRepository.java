package com.example.test_clever.repo;

import com.example.test_clever.domain.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    List<PatientProfile> findAllByStatusIdIn(Collection<Short> statuses);
    List<PatientProfile> findByOldClientGuidContaining(String guid);
}