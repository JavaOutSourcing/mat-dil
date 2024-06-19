package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.UserRequestDto;
import com.sparta.mat_dil.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto requestDto){

        userService.createUser(requestDto);
        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }

//    @PatchMapping
//    public ResponseEntity<String> withdrawUser(@Valid @RequestBody PasswordRequestDto requestDTO,
//        @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        userService.withdrawUser(requestDTO, userDetails.getUser());
//        return ResponseEntity.ok("회원탈퇴에 성공하였습니다.");
//    }



}
