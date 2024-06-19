package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
