package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
