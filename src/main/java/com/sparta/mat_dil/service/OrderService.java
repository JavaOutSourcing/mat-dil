package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.OrderDetailDataDto;
import com.sparta.mat_dil.dto.OrderResponseDto;
import com.sparta.mat_dil.entity.*;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.FoodRepository;
import com.sparta.mat_dil.repository.OrderDetailsRepository;
import com.sparta.mat_dil.repository.OrderRepository;
import com.sparta.mat_dil.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    @Transactional
    public OrderDetailDataDto<List<OrderResponseDto>> create(Long restaurantsId, List<Long> foodIdList, User user) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).get();
        Order order = new Order(user, restaurant);
        orderRepository.save(order);

        List<Food> foodList = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < foodIdList.size(); i++) {
            Food food = foodRepository.findById(foodIdList.get(i)).orElseThrow(()->new CustomException(ErrorType.NOT_FOUND_FOOD));
            OrderDetails orderDetails = new OrderDetails(order, food, food.getPrice());
            foodList.add(food);
            order.sumPrice(food.getPrice());
            sum += food.getPrice();
            orderDetailsRepository.save(orderDetails);
        }

        List<OrderResponseDto> orderList = new ArrayList<>();
        for(Food food : foodList) {
            OrderResponseDto orders = new OrderResponseDto(food);
            orderList.add(orders);
        }

        OrderDetailDataDto<List<OrderResponseDto>> orderDetailDataDto = new OrderDetailDataDto<>(ResponseStatus.FOOD_CHECK_SUCCESS, orderList, sum);

        return orderDetailDataDto;

    }


}
