package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.UserStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByUser_UserStatus(Pageable pageable);
}
