package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.*;
import com.sparta.mat_dil.entity.Food;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserType;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.FoodRepository;
import com.sparta.mat_dil.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

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
//        return null;
        return new PageImpl<>(restaurantRepository.findAll(pageable).stream().map(RestaurantResponseDto::new).collect(Collectors.toList()));
    }


    /** [getRestaurant()] 특정 음식점 조회
     * @param id 페이지 개수
     * @return 음식점 정보
     **/
    public SingleRestaurantResponseDto getRestaurant(Long id) {
        //음식점 확인 로직
        Restaurant restaurantInfo = this.findById(id);
        return new SingleRestaurantResponseDto(restaurantInfo);
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
        if(loginUser.getUserType() != UserType.SUPPLIER || !restaurantInfo.getUser().getAccountId().equals(loginUser.getAccountId())) {
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }
    }

    public FoodResponseDto saleFood(Long restaurantsId, FoodRequestDto foodRequestDto, User loginUser) {

        //해당 음식점이 없는 경우
        Restaurant restaurantById= restaurantRepository.findById(restaurantsId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_RESTAURANT));

        //등록을 시도하는 점주 정보와 해당 음식점 점주 정보가 같지 않는 경우
        if(!loginUser.getAccountId().equals(restaurantById.getUser().getAccountId())){
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }

        Food food=new Food(restaurantById, foodRequestDto);
        foodRepository.save(food);
        return new FoodResponseDto(food);
    }

    public Page<FoodResponseDto> getFoodList(int page, Long restaurantId) {
        //음식 등록 순으로 정렬
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        Pageable pageable = PageRequest.of(page, 10, sort);
        return new PageImpl<>(foodRepository.findByRestaurantId(pageable, restaurantId).stream().map(FoodResponseDto::new).collect(Collectors.toList()));
    }

    public FoodResponseDto getFood(Long restaurantsId, Long foodId) {
        Food food=foodRepository.findByIdAndRestaurant_Id(foodId, restaurantsId).orElseThrow(()->
                new CustomException(ErrorType.NOT_FOUND_FOOD));

        return new FoodResponseDto(food);
    }

    @Transactional
    public FoodResponseDto updateFood(Long restaurantId, Long foodId, FoodRequestDto requestDto, User loginUser) {
        //해당 음식점이 없는 경우
        Restaurant restaurantById= restaurantRepository.findById(restaurantId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_RESTAURANT));
        //수정하려는 음식이 없는 경우
        Food food=foodRepository.findById(foodId).orElseThrow(()->
                new CustomException(ErrorType.NOT_FOUND_FOOD));
        //수정을 시도하는 점주 정보와 해당 음식점 점주 정보가 같지 않는 경우
        if(!loginUser.getAccountId().equals(restaurantById.getUser().getAccountId())){
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }
        food.update(requestDto);
        return new FoodResponseDto(food);
    }

    @Transactional
    public void deleteFood(Long restaurantId, Long foodId, User loginUser) {
        //해당 음식점이 없는 경우
        Restaurant restaurantById= restaurantRepository.findById(restaurantId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_RESTAURANT));
        //수정하려는 음식이 없는 경우
        Food food=foodRepository.findById(foodId).orElseThrow(()->
                new CustomException(ErrorType.NOT_FOUND_FOOD));

        checkRestaurantSupplier(restaurantById, loginUser);

        foodRepository.delete(food);
    }
}
