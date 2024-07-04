package com.sparta.mat_dil.repository.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mat_dil.entity.CommentLike;
import com.sparta.mat_dil.entity.QCommentLike;
import com.sparta.mat_dil.repository.CommentLikeRepositoryQuery;
import com.sparta.mat_dil.util.QueryDslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;


@RequiredArgsConstructor
@Component
public class CommentLikeRepositoryQueryImpl implements CommentLikeRepositoryQuery {
    private final JPAQueryFactory queryFactory;

    @Override
    public Collection<CommentLike> getAllLikeComment(String accountId, Pageable pageable) {
        QCommentLike commentLike = QCommentLike.commentLike;
        List<OrderSpecifier> ORDERS= getAllOrderSpecifiers(pageable);
        return queryFactory.selectFrom(commentLike)
                .where(commentLike.user.accountId.eq(accountId))
                //orderBy가 List<>를 지원하지 않아 배열로 바꿔준다
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                //Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                Order direction = Order.DESC;
                switch (order.getProperty()) {
                    case "createdAt":
                        OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(direction, QCommentLike.commentLike, "createdAt");
                        ORDERS.add(orderCreatedAt);
                        break;
                    default:
                        break;
                }
            }
        }

        return ORDERS;
    }
}
