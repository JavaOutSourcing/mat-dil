package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.service.CommentService;
import com.sparta.mat_dil.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final CommentService commentService;

}
