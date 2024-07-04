package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.User;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
//내가 좋아요한 게시글/댓글 수 필드 추가 - 필수 구현
public class ProfileResponseDto {
    private final String accountId;
    @Getter(value = AccessLevel.PRIVATE)
    private final String password;
    private final String name;
    private final String intro;
    private final String role;
    private final Long restaurantLikes;
    private final Long commentLikes;
    private final Long followersCnt;
    private final Long totalLikes;

    public ProfileResponseDto(User user) {
        this.accountId = user.getAccountId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.intro = user.getIntro();
        this.role = String.valueOf(user.getUserType());
        this.commentLikes=user.getCommentLikes();
        this.restaurantLikes=user.getRestaurantLikes();
        this.followersCnt=user.getFollowersCnt();
        this.totalLikes=user.getTotalLikes();
    }
}
