package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Food;
import lombok.Getter;

@Getter
public class FoodResponseDto {
    private String foodName;
    private int price;
    private String description;

    public FoodResponseDto(Food food){
        this.foodName=food.getFoodName();
        this.price=food.getPrice();
        this.description=food.getDescription();
    }
}
