package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SingleCommentResponseDto {
    private final String accountId;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long likes;

    public SingleCommentResponseDto(Comment comment){
        this.accountId = comment.getUser().getAccountId();
        this.name = comment.getUser().getName();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likes=comment.getLikes();
    }
}