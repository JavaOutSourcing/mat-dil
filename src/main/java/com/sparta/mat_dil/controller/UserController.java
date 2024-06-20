package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.ProfileRequestDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.dto.ResponseMessageDto;
import com.sparta.mat_dil.dto.UserRequestDto;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    @PostMapping("/logout")
    public ResponseEntity<ResponseMessageDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
            userService.logout(userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.LOGOUT_SUCCESS));
    }

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


}
