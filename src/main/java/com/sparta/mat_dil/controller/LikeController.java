package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.LikeCommentResponseDto;
import com.sparta.mat_dil.dto.LikeResponseDto;
import com.sparta.mat_dil.dto.LikeRestaurantResponseDto;
import com.sparta.mat_dil.dto.ResponseDataDto;
import com.sparta.mat_dil.enums.ContentTypeEnum;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.CommentLikeService;
import com.sparta.mat_dil.service.LikeService;
import com.sparta.mat_dil.service.RestaurantLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final RestaurantLikeService restaurantLikeService;
    private final CommentLikeService commentLikeService;

    // 음식점/댓글에 좋아요 하기 - 필수 구현
    @PutMapping("/{contentType}/{contentId}")
    public ResponseEntity<ResponseDataDto<LikeResponseDto>> updatetLike(@PathVariable("contentType") ContentTypeEnum contentType, @PathVariable("contentId") Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {

        LikeResponseDto likeResponseDto;

        if (contentType.equals(ContentTypeEnum.RESTAURANT)) {
            likeResponseDto = likeService.updateRestaurantLike(contentId, userDetails.getUser());
        } else {
            likeResponseDto = likeService.updateCommentLike(contentId, userDetails.getUser());
        }

        if (likeResponseDto.isLiked()) {
            return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIKE_CREATE_SUCCESS, likeResponseDto));
        } else {
            return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIKE_DELETE_SUCCESS, likeResponseDto));
        }
    }

    //내가 좋아요한 음식점 목록 조회 - 필수 구현
    @GetMapping("/restaurant")
    public ResponseEntity<ResponseDataDto<LikeRestaurantResponseDto>> getLikeRestaurantList(@RequestParam(value = "page") int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIKE_SELECT_SUCCESS,restaurantLikeService.getLikeRestaurantList(page - 1, userDetails.getUser())));

    }

    //내가 좋아요한 댓글 목록 조회
    @GetMapping("/comment")
    public ResponseEntity<ResponseDataDto<LikeCommentResponseDto>> getLikeCommentList(@RequestParam(value = "page") int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIKE_SELECT_SUCCESS,commentLikeService.getLikeCommentList(page - 1, userDetails.getUser())));
    }
}
