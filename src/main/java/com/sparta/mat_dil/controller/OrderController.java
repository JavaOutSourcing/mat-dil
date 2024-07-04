package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.OrderDetailDataDto;
import com.sparta.mat_dil.dto.OrderRequestDto;
import com.sparta.mat_dil.dto.OrderResponseDto;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.repository.OrderRepository;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/{restaurantsId}")
    public ResponseEntity<OrderDetailDataDto<List<OrderResponseDto>>> createOrder(@PathVariable Long restaurantsId, @RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderDetailDataDto<List<OrderResponseDto>> orderDetails = orderService.create(restaurantsId, requestDto.getFoodId(), userDetails.getUser());
        return ResponseEntity.ok(new OrderDetailDataDto<>(ResponseStatus.ORDER_CHECK_SUCCESS, orderDetails, orderDetails.getTotalPrice()).getData());
    }

}
