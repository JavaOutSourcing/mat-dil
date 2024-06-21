package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.*;
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

    @GetMapping
    public ResponseEntity<ResponseMessageDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.getProfile(userDetails.getUser().getId());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.PROFILE_CHECK_SUCCESS));
    }
//    @PatchMapping
    @PatchMapping
    public ResponseEntity<ResponseMessageDto> profile1Update(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProfileRequestDto requestDto) {
        userService.update(userDetails.getUser().getId(), requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.PROFILE_UPDATE_SUCCESS));
    }


}
