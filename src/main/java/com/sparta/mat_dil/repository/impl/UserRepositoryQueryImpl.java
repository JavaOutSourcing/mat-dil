package com.sparta.mat_dil.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.entity.QUser;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.repository.UserRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserRepositoryQueryImpl implements UserRepositoryQuery {
    private final JPAQueryFactory queryFactory;

   @Override
    public List<ProfileResponseDto> findTop10ByOrderByFollowersCntDesc(){
        QUser user = QUser.user;
                List<User> users=queryFactory
                .selectFrom(user)
                .orderBy(user.followersCnt.desc())
                .limit(10)
                .fetch();
       List<ProfileResponseDto> results=users.stream().map(ProfileResponseDto::new).toList();
       return results;
    }
}
