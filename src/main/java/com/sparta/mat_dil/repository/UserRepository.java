package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery{
    Optional<User> findByAccountId(String accountId);

    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long kakaoId);
}
