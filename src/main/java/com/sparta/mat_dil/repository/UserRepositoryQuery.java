package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.dto.ProfileResponseDto;

import java.util.List;

public interface UserRepositoryQuery {
    List<ProfileResponseDto> findTop10ByOrderByFollowersCntDesc();
}
