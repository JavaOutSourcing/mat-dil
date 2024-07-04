package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.dto.ResponseMessageDto;
import com.sparta.mat_dil.dto.RestaurantResponseDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    //내가 팔로우한 사람의 게시글 조회하기, 정렬 기준 추가 - 추가 구현
    @GetMapping
    public Page<RestaurantResponseDto> getFollowingPost(@RequestParam(value = "page") int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.getFollowingPost(page - 1, userDetails.getUser());
    }

    //팔로우 가장 많은 사람 상위 10명 조회 - 명예의 전당
    @GetMapping("/followers")
    public List<ProfileResponseDto> findTop10ByOrderByFollowersCntDesc() {
        return followService.findTop10ByOrderByFollowersCntDesc();
    }
}
