package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.RestaurantLike;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface RestaurantLikeRepositoryQuery {
    Collection<RestaurantLike> getAllLikeRestaurant(String accountId, Pageable pageable);

}