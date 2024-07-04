package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.CommentRequestDto;
import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.dto.SingleCommentResponseDto;
import com.sparta.mat_dil.entity.Comment;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.CommentRepository;
import com.sparta.mat_dil.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    // 댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long restaurantId, CommentRequestDto requestDto, User user) {
        // 음식점이 존재하는지 확인
        Restaurant restaurant = validateRestaurantId(restaurantId);

        //본인 음식점 검증
        if(restaurant.getUser().getAccountId().equals(user.getAccountId())){
            throw new CustomException(ErrorType.NOT_ACCEPTED_COMMENT);
        }

        Comment comment = Comment.builder()
                .user(user)
                .restaurant(restaurant)
                .description(requestDto.getDescription())
                .build();
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllComments(Long restaurantId) {
        // 음식점이 존재하는지 확인
        Restaurant restaurant = validateRestaurantId(restaurantId);

        List<Comment> commentList = commentRepository.findAllByRestaurant(restaurant);

        return commentList.stream().map(CommentResponseDto::new).toList();
    }

    // 단일 댓글 조회
    @Transactional(readOnly = true)
    public SingleCommentResponseDto getComment(Long restaurantId, Long commentId) {
        // 음식점이 존재하는지 확인
        validateRestaurantId(restaurantId);

        // 댓글 존재 검증
        Comment comment = validateComment(commentId);

        return new SingleCommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long restaurantId, Long commentId, User user, CommentRequestDto requestDto) {
        // 음식점이 존재하는지 확인
        validateRestaurantId(restaurantId);

        // 댓글 존재 검증
        Comment comment = validateComment(commentId);

        // 댓글 작성자 검증
        checkUserComment(user, comment);

        comment.update(requestDto.getDescription());

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long restaurantId, Long commentId, User user) {
        // 음식점이 존재하는지 확인
        validateRestaurantId(restaurantId);

        // 댓글 존재 검증
        Comment comment = validateComment(commentId);

        // 댓글 작성자 검증
        checkUserComment(user, comment);

        commentRepository.delete(comment);
    }

    // 음식점 존재 확인
    private Restaurant validateRestaurantId(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_RESTAURANT));
    }

    // 댓글 존재 검증
    private Comment validateComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
    }

    // 댓글 작성자 검증
    private void checkUserComment(User user, Comment comment) {
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new CustomException(ErrorType.NO_ATUTHENTIFICATION);
        }
    }
}