package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

}
