package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.CommentRequestDto;
import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.dto.PasswordRequestDto;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.service.CommentService;
import com.sparta.mat_dil.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final CommentService commentService;

    //댓글 등록
//    @PostMapping("/{restaurants_id}/comments")
//    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long restaurants_id, @RequestBody CommentRequestDto requestDto,
//                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        CommentResponseDto responseDto = commentService.createComment(restaurants_id, requestDto, userDetails.getUser());
//
//        return ResponseEntity.ok(responseDto);
//    }

    //전체 댓글 조회
    @GetMapping("/{restaurantId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(@PathVariable Long restaurantId){
        List<CommentResponseDto> responseDtoList = commentService.getAllComments(restaurantId);

        return ResponseEntity.ok(responseDtoList);
    }

    //단일 댓글 조회
    @GetMapping("/{restaurantId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long restaurantId, @PathVariable Long commentId){
        CommentResponseDto responseDto = commentService.getComment(restaurantId, commentId);

        return ResponseEntity.ok(responseDto);
    }

//    //댓글 수정
//    @PutMapping("/{restaurantId}/comments/{commentId}")
//    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long restaurantId, @PathVariable Long commentId,
//                                                            @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        CommentResponseDto responseDto = commentService.updateComment(restaurantId, commentId, userDetails.getUser(), requestDto);
//
//        return ResponseEntity.ok(responseDto);
//    }
//
//    //댓글 삭제
//    @DeleteMapping("/{restaurantId}/comments/{commentId}")
//    public ResponseEntity<String> deleteComment(@PathVariable Long restaurantId, @PathVariable Long commentId,
//                                                @RequestBody PasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        commentService.deleteComment(restaurantId, commentId, requestDto, userDetails.getUser());
//
//        return ResponseEntity.ok("댓글이 삭제되었습니다.");
//    }

}
