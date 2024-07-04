package com.sparta.mat_dil.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mat_dil.entity.Follow;
import com.sparta.mat_dil.entity.QFollow;
import com.sparta.mat_dil.entity.QRestaurant;
import com.sparta.mat_dil.entity.Restaurant;
import com.sparta.mat_dil.repository.FollowRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery {
    private final JPAQueryFactory queryFactory;
    QFollow follow = QFollow.follow;
    QRestaurant restaurant=QRestaurant.restaurant;

    //querydsl로 follow 테이블에서 팔로우 거는 사람(나)과 팔로우 받는 사람(상대)의 아이디를 찾아서 follow 객체를 반환
    @Override
    public Optional<Follow> findByFromUserAccountIdAndToUserAccountId(String followedAccountId, String myAccountId) {
        Follow result= queryFactory.selectFrom(follow)
                .where(fromUserEq(myAccountId), toUserEq(followedAccountId))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Restaurant> findAllByToUserAccountId(String accountId, Pageable pageable) {
        return queryFactory.select(restaurant)
                .from(follow)
                .join(restaurant).on(follow.toUser.accountId.eq(restaurant.user.accountId))
                .where(follow.fromUser.accountId.eq(accountId))
                .orderBy(restaurant.restaurantName.asc(), restaurant.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression fromUserEq(String myAccountId){
        if(!StringUtils.hasText(myAccountId)) return null;
        return follow.fromUser.accountId.eq(myAccountId);

    }

    private BooleanExpression toUserEq(String followedAccountId){
        if(!StringUtils.hasText(followedAccountId)) return null;
        return follow.toUser.accountId.eq(followedAccountId);

    }

}

