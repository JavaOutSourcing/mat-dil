package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.ProfileRequestDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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
