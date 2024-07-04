package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Follow;
import com.sparta.mat_dil.entity.Restaurant;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FollowRepositoryQuery {
    Optional<Follow> findByFromUserAccountIdAndToUserAccountId(String followedAccountId, String myAccountId);

    List<Restaurant> findAllByToUserAccountId(String accountId, Pageable pageable);
}