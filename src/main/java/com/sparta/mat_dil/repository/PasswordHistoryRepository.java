package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
}
