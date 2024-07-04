package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class LikeRestaurantResponseDto {
    private final int currentPage;
    private final List<RestaurantResponseDto> restaurantList;

    public static LikeRestaurantResponseDto of(int currentPage, Page<RestaurantResponseDto> restaurants) {
        return LikeRestaurantResponseDto.builder()
                .currentPage(currentPage)
                .restaurantList(restaurants.getContent().stream().toList())
                .build();
    }
}
