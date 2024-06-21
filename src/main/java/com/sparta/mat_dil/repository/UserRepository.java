package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
    Optional<User> findByAccountId(String id);

    Optional<User> findByEmail(String email);
=======
    Optional<User> findByUsername(String username);
>>>>>>> a0eeb25 (jwt commit)
}
