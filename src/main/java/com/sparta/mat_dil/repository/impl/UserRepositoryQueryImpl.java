package com.sparta.mat_dil.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.entity.QRestaurant;
import com.sparta.mat_dil.entity.QUser;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.UserRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserRepositoryQueryImpl implements UserRepositoryQuery {
    private final JPAQueryFactory queryFactory;

   @Override
    public List<ProfileResponseDto> findTop10ByOrderByFollowersCntDesc(){
        QRestaurant user = QRestaurant.restaurant;
        List<Restaurant> users = queryFactory
                //.select(Projections.constructor(ProfileResponseDto.class, user))
                .selectFrom(user)
                //.orderBy(user.followersCnt.desc())
                .limit(10)
                .fetch();

        return List.of();
//        users
//                .stream()
//                .map(ProfileResponseDto::new)
//                .toList();
      //  return  users;

//                List<User> users=queryFactory
//                .selectFrom(user)
//                .orderBy(user.followersCnt.desc())
//                .limit(10)
//                .fetch();
//        List<ProfileResponseDto> results=users.stream().map(ProfileResponseDto::new).toList();
    }
}
