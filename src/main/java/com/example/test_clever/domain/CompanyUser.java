package com.example.test_clever.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_user",
        uniqueConstraints = @UniqueConstraint(name = "company_user_uniq_login", columnNames = "login"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String login;
}
