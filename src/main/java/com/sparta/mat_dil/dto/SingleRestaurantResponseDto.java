package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Restaurant;
import lombok.Getter;

@Getter
public class SingleRestaurantResponseDto {
    private String restaurantName;
    private String description;
    private Long likes;

    public SingleRestaurantResponseDto(Restaurant restaurant) {
        this.restaurantName = restaurant.getRestaurantName();
        this.description = restaurant.getDescription();
        this.likes=restaurant.getLikes();
    }

}
