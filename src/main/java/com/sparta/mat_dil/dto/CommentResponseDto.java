package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.Comment;
import com.sparta.mat_dil.entity.CommentLike;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final String accountId;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment){
        this.accountId = comment.getUser().getAccountId();
        this.name = comment.getUser().getName();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
    public CommentResponseDto(CommentLike commentLike){
        this.accountId = commentLike.getUser().getAccountId();
        this.name = commentLike.getUser().getName();
        this.description = commentLike.getComment().getDescription();
        this.createdAt = commentLike.getCreatedAt();
        this.modifiedAt = commentLike.getModifiedAt();
    }

}
