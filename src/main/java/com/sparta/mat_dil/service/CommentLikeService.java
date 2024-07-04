package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.CommentResponseDto;
import com.sparta.mat_dil.dto.LikeCommentResponseDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    //좋아요한 댓글 리스트
    public LikeCommentResponseDto getLikeCommentList(int page, User user) {
        //댓글 등록 순으로 정렬
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);

        //return new PageImpl<>(commentLikeRepository.findAllById(user.getAccountId(), pageable).stream().map(CommentResponseDto::new).collect(Collectors.toList()));
        Page<CommentResponseDto> responseDto= new PageImpl<>(commentLikeRepository.getAllLikeComment(user.getAccountId(), pageable).stream().map(CommentResponseDto::new).collect(Collectors.toList()));
        return LikeCommentResponseDto.of(page, responseDto);
    }
}

