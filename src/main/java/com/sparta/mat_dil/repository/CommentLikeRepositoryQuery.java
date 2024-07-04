package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.CommentLike;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CommentLikeRepositoryQuery {
    Collection<CommentLike> getAllLikeComment(String accountId, Pageable pageable);
}
