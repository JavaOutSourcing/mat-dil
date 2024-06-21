package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.dto.RestaurantRequestDto;
import com.sparta.mat_dil.dto.RestaurantResponseDto;
import com.sparta.mat_dil.entity.Restaurant;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findAll(Pageable pageable);
}
