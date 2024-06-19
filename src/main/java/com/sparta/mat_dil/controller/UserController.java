package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.UserRequestDto;
import com.sparta.mat_dil.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto requestDto){

    }


}
