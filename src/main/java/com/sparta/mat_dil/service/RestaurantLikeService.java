package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.LikeRestaurantResponseDto;
import com.sparta.mat_dil.dto.RestaurantResponseDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.RestaurantLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantLikeService {
    private final RestaurantLikeRepository restaurantLikeRepository;

    public LikeRestaurantResponseDto getLikeRestaurantList(int page, User user) {
        //음식점 등록 순으로 정렬
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        //restaurantLikeRepository.getLikeRestaurantList(pageable, user);
        //return new PageImpl<>(restaurantLikeRepository.findAllById(user.getAccountId(), pageable).stream().map(RestaurantResponseDto::new).collect(Collectors.toList()));
        Page<RestaurantResponseDto> responseDto= new PageImpl<>(restaurantLikeRepository.getAllLikeRestaurant(user.getAccountId(), pageable).stream().map(RestaurantResponseDto::new).collect(Collectors.toList()));
        return LikeRestaurantResponseDto.of(page, responseDto);


    }
}
