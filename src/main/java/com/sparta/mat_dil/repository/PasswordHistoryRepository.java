package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.PasswordHistory;
import com.sparta.mat_dil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findTop3ByUserOrderByChangeDateDesc(User user);
}
