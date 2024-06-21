package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.ProfileRequestDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.dto.ResponseMessageDto;
import com.sparta.mat_dil.service.UserService;
import com.sparta.mat_dil.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //시큐리티 적용 전
/*    @GetMapping("/{userId}")
    public ResponseEntity<ResponseMessageDto> getProfile(@PathVariable Long userId) {
        userService.getProfile(userId);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.PROFILE_CHECK_SUCCESS));
    }*/

    @GetMapping
    public ResponseEntity<ResponseMessageDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.getProfile(userDetails.getUser().getId());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.PROFILE_CHECK_SUCCESS));
    }

    //시큐리티 적용 전
 /*   @PutMapping("/{userId}")
    public ResponseEntity<ResponseMessageDto> profileUpdate(
            @PathVariable Long userId,
            @RequestBody ProfileRequestDto requestDto) {
        userService.update(userId, requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.PROFILE_UPDATE_SUCCESS));
    }*/

    //시큐리티 적용 시
    @PatchMapping
    public ResponseEntity<ResponseMessageDto> profile1Update(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProfileRequestDto requestDto) {
        userService.update(userDetails.getUser().getId(), requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.PROFILE_UPDATE_SUCCESS));
    }


}
