package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.OrderResponseDto;
import com.sparta.mat_dil.entity.Food;
import com.sparta.mat_dil.entity.Order;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.FoodRepository;
import com.sparta.mat_dil.repository.OrderRepository;
import com.sparta.mat_dil.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public List<OrderResponseDto> create(Long restaurantsId, List<Long> foodIdList) {

        List<Food> foodList = new ArrayList<>();
        for (int i = 0; i < foodIdList.size(); i++) {
            Food food = foodRepository.findById(foodIdList.get(i)).orElseThrow(()->new CustomException(ErrorType.NOT_FOUND_FOOD));
            foodList.add(food);
        }

        List<OrderResponseDto> orderList = new ArrayList<>();
        for(Food food : foodList) {
            OrderResponseDto order = new OrderResponseDto(food);
            orderList.add(order);
        }

        return orderList;

    }


}
