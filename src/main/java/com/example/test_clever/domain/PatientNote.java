package com.example.test_clever.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "patient_note",
        uniqueConstraints = @UniqueConstraint(name = "uq_patient_note_legacy_guid", columnNames = "legacy_guid"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time", nullable = false)
    private OffsetDateTime createdDateTime;

    @Column(name = "last_modified_date_time", nullable = false)
    private OffsetDateTime lastModifiedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id") // FK уже есть в БД
    private CompanyUser createdByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_user_id") // FK уже есть в БД
    private CompanyUser lastModifiedByUser;

    @Column(name = "note", length = 4000)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id") // FK уже есть в БД
    private PatientProfile patient;

    @Column(name = "legacy_guid", length = 36, unique = true)
    private String legacyGuid;
}
