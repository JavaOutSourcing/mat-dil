package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
