package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.RestaurantLike;
import com.sparta.mat_dil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantLikeRepository extends JpaRepository<RestaurantLike, Long>, RestaurantLikeRepositoryQuery{
    Optional<RestaurantLike> findByUserAndRestaurant(User user, Restaurant restaurant);
}