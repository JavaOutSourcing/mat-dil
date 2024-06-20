package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.dto.RestaurantRequestDto;
import com.sparta.mat_dil.dto.RestaurantResponseDto;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserStatus;
import com.sparta.mat_dil.entity.UserType;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.RestaurantRepository;
import com.sparta.mat_dil.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    /** [createRestaurant()] 음식점 등록
     * @param loginUser 로그인 회원 정보
     * @param requestDto 등록할 음식점 정보
     * @return 음식점 정보
    **/
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto requestDto, User loginUser) {
        //UserType 이 판매자가 아닐경우 [권한없음 예외처리]
        if (!loginUser.getUserType().equals(UserType.SUPPLIER)) {
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }

        Restaurant restaurant = new Restaurant(loginUser, requestDto);

        restaurantRepository.save(restaurant);

        return new RestaurantResponseDto(restaurant);
    }


    /** [getNewsfeed()] 음식점 조회
    * @param page 페이지 개수
    * @return 음식점 정보
    **/
    public Page<RestaurantResponseDto> getNewsfeed(int page) {
        //음식점 등록 순으로 정렬
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);

        return new PageImpl<>(restaurantRepository.findAllByUser_UserStatus(pageable).stream().map(RestaurantResponseDto::new).collect(Collectors.toList()));
    }
}
