package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.CommentRequestDto;
import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.UserRepository;
import com.sparta.mat_dil.service.CommentService;
import com.sparta.mat_dil.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final CommentService commentService;

    private final UserRepository userRepository;

//    @PostMapping("/{restaurants_id}/comments")
//    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long restaurants_id, @RequestBody CommentRequestDto requestDto,
//                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        CommentResponseDto responseDto = commentService.createComment(restaurants_id, requestDto, userDetails.getUser());
//
//        return ResponseEntity.ok(responseDto);
//    }
}
