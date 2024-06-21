package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.*;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createUser(@Valid @RequestBody UserRequestDto requestDto){

        userService.createUser(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.SIGN_UP_SUCCESS));
    }

    //회원 탈퇴
//    @PatchMapping
//    public ResponseEntity<ResponseMessageDto> withdrawUser(@Valid @RequestBody PasswordRequestDto requestDTO,
//        @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        userService.withdrawUser(requestDTO, userDetails.getUser());
//        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DEACTIVATE_USER_SUCCESS));
//    }



    @GetMapping("/{userId}")
//    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(userService.getProfile(userId));
//        return ResponseEntity.ok().body(userService.getProfile(userDetails.getUser().getId()));
    }

//    @PatchMapping
    @PatchMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> profileUpdate(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId,
            @RequestBody ProfileRequestDto requestDto) {

//        return ResponseEntity.ok().body(userService.update(userDetails.getUser().getId(), requestDto));
        return ResponseEntity.ok().body(userService.update(userId, requestDto));
    }

//    @PostMapping("/login")
//    public

}
