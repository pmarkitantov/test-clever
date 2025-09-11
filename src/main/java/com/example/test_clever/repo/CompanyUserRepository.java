package com.example.test_clever.repo;
import com.example.test_clever.domain.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    Optional<CompanyUser> findByLogin(String login);
    boolean existsByLogin(String login);
}