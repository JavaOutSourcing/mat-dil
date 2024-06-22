package com.sparta.mat_dil.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoodRequestDto {
    private String foodname;
    private int price;
    private String description;
}
