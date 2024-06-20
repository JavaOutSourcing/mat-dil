package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsByRefreshToken(String token);
}
