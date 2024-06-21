package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Food;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private String foodName;
    private int price;

    public OrderResponseDto(Food food) {
        this.foodName = food.getFoodName();
        this.price = food.getPrice();
    }
}
