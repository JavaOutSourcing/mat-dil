package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Comment;
import com.sparta.mat_dil.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRestaurant(Restaurant restaurant);
}
