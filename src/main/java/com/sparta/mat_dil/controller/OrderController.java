package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.OrderDetailDataDto;
import com.sparta.mat_dil.dto.OrderRequestDto;
import com.sparta.mat_dil.dto.OrderResponseDto;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{restaurantsId}")
    public ResponseEntity<OrderDetailDataDto<List<OrderResponseDto>>> createOrder(@PathVariable Long restaurantsId, @RequestBody OrderRequestDto requestDto) {
        List<OrderResponseDto> orderList = orderService.create(restaurantsId, requestDto.getFoodId());
        int sum = 0;
        for (OrderResponseDto order : orderList) {
            sum += order.getPrice();
        }
        return ResponseEntity.ok(new OrderDetailDataDto<>(ResponseStatus.ORDER_SUCCESS, orderList, sum));
    }

}
