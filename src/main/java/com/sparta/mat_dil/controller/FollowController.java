package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.FollowingResponseDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.dto.ResponseDataDto;
import com.sparta.mat_dil.dto.ResponseMessageDto;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowController {
    private final FollowService followService;

    //팔로우 하기 - 추가 구현
    @PostMapping("/{accountId}")
    public ResponseEntity<ResponseMessageDto> createFollowing(@PathVariable String accountId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseMessageDto responseMessageDto=followService.createFollowing(accountId, userDetails.getUser());
        return ResponseEntity.ok(responseMessageDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDataDto<FollowingResponseDto>> getFollowingPost(@RequestParam(value = "page") int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.FOLLOWING_GET_POSTS_SUCCESS, followService.getFollowingPost(page - 1, userDetails.getUser())));

    }

    //팔로우 가장 많은 사람 상위 10명 조회 - 명예의 전당
    @GetMapping("/followers")
    public List<ProfileResponseDto> findTop10ByOrderByFollowersCntDesc() {
        return followService.findTop10ByOrderByFollowersCntDesc();
    }
}
