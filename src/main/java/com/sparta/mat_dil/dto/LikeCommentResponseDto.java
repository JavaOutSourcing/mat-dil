package com.sparta.mat_dil.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class LikeCommentResponseDto {
    private final int currentPage;
    private final List<CommentResponseDto> commentList;

    public static LikeCommentResponseDto of(int currentPage, Page<CommentResponseDto> comments) {
        return LikeCommentResponseDto.builder()
                .currentPage(currentPage)
                .commentList(comments.getContent().stream().toList())
                .build();
    }
}
