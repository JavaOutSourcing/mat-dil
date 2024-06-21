package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.CommentRequestDto;
import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.dto.PasswordRequestDto;
import com.sparta.mat_dil.entity.Comment;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.CommentRepository;
import com.sparta.mat_dil.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestaurantRepository restaurantRepository;
//    private final PasswordEncoder passwordEncoder;

    // 댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long restaurantId, CommentRequestDto requestDto, User user) {
        // 음식점이 존재하는지 확인
        Restaurant restaurant = validateRestaurantId(restaurantId);

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
    public CommentResponseDto getComment(Long restaurantId, Long commentId) {
        // 음식점이 존재하는지 확인
        validateRestaurantId(restaurantId);

        // 댓글 존재 검증
        Comment comment = validateComment(commentId);

        return new CommentResponseDto(comment);
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
    public void deleteComment(Long restaurantId, Long commentId, User user, PasswordRequestDto requestDto) {
        // 음식점이 존재하는지 확인
        validateRestaurantId(restaurantId);

        // 댓글 존재 검증
        Comment comment = validateComment(commentId);

        // 댓글 작성자 검증
        checkUserComment(user, comment);

        // 비밀번호 검증
//        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
        commentRepository.delete(comment);
    }

    // 음식점 존재 확인
    private Restaurant validateRestaurantId(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 음식점이 존재하지 않습니다."));
    }

    // 댓글 존재 검증
    private Comment validateComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }

    // 댓글 작성자 검증
    private void checkUserComment(User user, Comment comment) {
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new IllegalArgumentException("수정할 권한이 없습니다.");
        }
    }
}