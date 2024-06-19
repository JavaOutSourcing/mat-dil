package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.CommentRequestDto;
import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.entity.Comment;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.CommentRepository;
import com.sparta.mat_dil.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestaurantRepository restaurantRepository;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long restaurantId, CommentRequestDto requestDto, User user){
        //음식점이 존재하는지 확인
        Restaurant restaurant = validateRestaurantId(restaurantId);

        Comment comment = Comment.builder()
                .user(user)
                .restaurant(restaurant)
                .description(requestDto.getDescription())
                .build();
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }



    //음식점 존재 확인
    private Restaurant validateRestaurantId(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if(restaurant.isEmpty()){
            throw new IllegalArgumentException("해당 음식점이 존재하지 않습니다.");
        }
        return restaurant.get();
    }
}
