package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
