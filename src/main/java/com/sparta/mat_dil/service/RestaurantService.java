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
import org.springframework.transaction.annotation.Transactional;

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
        checkSupplierPermission(loginUser);

        Restaurant restaurant = new Restaurant(loginUser, requestDto);

        restaurantRepository.save(restaurant);

        return new RestaurantResponseDto(restaurant);
    }


    /** [getRestaurantList()] 전체 음식점 조회
    * @param page 페이지 개수
    * @return 음식점 정보
    **/
    public Page<RestaurantResponseDto> getRestaurantList(int page) {
        //음식점 등록 순으로 정렬
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);

        return new PageImpl<>(restaurantRepository.findAllByUser_UserStatus(pageable).stream().map(RestaurantResponseDto::new).collect(Collectors.toList()));
    }


    /** [getRestaurant()] 특정 음식점 조회
    * @param id 페이지 개수
    * @return 음식점 정보
    **/
    public RestaurantResponseDto getRestaurant(Long id) {
        //음식점 확인 로직
        Restaurant restaurantInfo = this.findById(id);

        return new RestaurantResponseDto(restaurantInfo);
    }


    /** [updateRestaurant()] 특정 음식점 수정
     * @param id 페이지 개수
     * @return 음식점 정보
     **/
    @Transactional
    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto requestDto, User loginUser) {
        //음식점 확인 로직
        Restaurant restaurantInfo = this.findById(id);

        //판매자가 아니고, 게시물을 작성한 판매자가 아닌경우 예외처리
        checkRestaurantSupplier(restaurantInfo, loginUser);

        restaurantInfo.update(requestDto);

        return new RestaurantResponseDto(restaurantInfo);
    }



    /** [deleteRestaurant()] 특정 음식점 수정
     * @param id 음식점 정보
     * @return 음식점 정보
     **/
    @Transactional
    public void deleteRestaurant(Long id, User loginUser) {
        //음식점 확인 로직
        Restaurant restaurantInfo = this.findById(id);

        //판매자가 아니고, 게시물을 작성한 판매자가 아닌경우 예외처리
        checkRestaurantSupplier(restaurantInfo, loginUser);

        restaurantRepository.delete(restaurantInfo);
    }

    //음식점 존재 여부 확인
    public Restaurant findById(Long id){
        return restaurantRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_RESTAURANT)
        );
    }

    // 권한 확인 메서드 - 판매자가 아닐 경우 예외처리
    private void checkSupplierPermission(User user) {
        if (!user.getUserType().equals(UserType.SUPPLIER)) {
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }
    }

    // 권한 확인 메서드 - 판매자가 아니고, 게시물을 작성한 판매자가 아닌경우 예외처리
    private void checkRestaurantSupplier(Restaurant restaurantInfo, User loginUser) {
        if(loginUser.getUserType() == UserType.SUPPLIER && !restaurantInfo.getUser().getName().equals(loginUser.getName())) {
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }
    }
}
