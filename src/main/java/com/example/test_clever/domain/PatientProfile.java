package com.example.test_clever.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "old_client_guid", length = 255)
    private String oldClientGuid;

    @Column(name = "status_id", nullable = false)
    private Short statusId;
}