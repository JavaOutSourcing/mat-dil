package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponseDto {
    private String restaurantName;
    private String description;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.restaurantName = restaurant.getRestaurantName();
        this.description = restaurant.getDescription();
    }

}