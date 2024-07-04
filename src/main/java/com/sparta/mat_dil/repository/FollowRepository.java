package com.sparta.mat_dil.repository;


import com.sparta.mat_dil.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryQuery{

}