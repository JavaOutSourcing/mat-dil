package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.LikeResponseDto;
import com.sparta.mat_dil.enums.ContentTypeEnum;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/{contentType}/{contentId}")
    public ResponseEntity<String> updateRestaurantLike(@PathVariable("contentType") ContentTypeEnum contentType, @PathVariable("contentId") Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {

        LikeResponseDto likeResponseDto;

        if (contentType.equals(ContentTypeEnum.RESTAURANT)) {
            likeResponseDto = likeService.updateRestaurantLike(contentId, userDetails.getUser());
        } else {
            likeResponseDto = likeService.updateCommentLike(contentId, userDetails.getUser());
        }

        if (likeResponseDto.isLiked()) {
            return ResponseEntity.ok("좋아요 : " + likeResponseDto.getCnt());
        } else {
            return ResponseEntity.ok("좋아요 취소 : " + likeResponseDto.getCnt());
        }
    }
}
