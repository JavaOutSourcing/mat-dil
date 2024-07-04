package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByIdAndRestaurant_Id(Long id, Long restaurantId);

    Page<Food> findByRestaurantId(Pageable pageable, Long restaurantId);
}
