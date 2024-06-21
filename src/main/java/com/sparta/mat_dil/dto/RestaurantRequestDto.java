package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class RestaurantRequestDto {
    @NotBlank(message = "음식점 이름을 입력해주세요.")
    @Size(min = 1, max = 10, message = "음식점 이름은 최소 1글자에서 최대 10글자까지 가능합니다.")
    private String restaurantName;
    //
    @NotBlank(message = "음식점에 대해 설명해주세요.")
    @Size(min = 10, max = 30, message = "설명은 최소 10자에서 최대 30자까지 가능합니다.")
    private String description;
}