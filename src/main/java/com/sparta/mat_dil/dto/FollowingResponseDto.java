package com.sparta.mat_dil.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class FollowingResponseDto {
    private final int currentPage;
    private final List<RestaurantResponseDto> restaurantList;

    public static FollowingResponseDto of(int currentPage, Page<RestaurantResponseDto> restaurants) {
        return FollowingResponseDto.builder()
                .currentPage(currentPage)
                .restaurantList(restaurants.getContent().stream().toList())
                .build();
    }
}
