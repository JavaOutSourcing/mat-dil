package com.sparta.mat_dil.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    // [USER]
    // 회원가입
    SIGN_UP_SUCCESS(HttpStatus.OK, "회원가입에 성공 했습니다."),
    DEACTIVATE_USER_SUCCESS(HttpStatus.OK, "회원탈퇴에 성공하였습니다."),
    // 로그인
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공 했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공 했습니다."),
    // 프로필
    PROFILE_CHECK_SUCCESS(HttpStatus.OK, "프로필을 조회합니다."),
    PROFILE_UPDATE_SUCCESS(HttpStatus.OK, "프로필이 정상적으로 수정 되었습니다."),
    PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호가 정상적으로 수정 되었습니다."),
    // 음식점
    RESTAURANT_CREATE_SUCCESS(HttpStatus.OK, "음식점 등록에 성공하였습니다."),
    RESTAURANT_CHECK_SUCCESS(HttpStatus.OK, "음식점 조회에 성공하였습니다."),
    RESTAURANT_UPDATE_SUCCESS(HttpStatus.OK, "음식점 수정에 성공하였습니다."),
    RESTAURANT_DELETE_SUCCESS(HttpStatus.OK, "음식점이 삭제되었습니다."),
    // 음식
    FOOD_CREATE_SUCCESS(HttpStatus.OK, "음식 등록에 성공하였습니다."),
    FOOD_CHECK_SUCCESS(HttpStatus.OK, "음식 조회에 성공하였습니다."),
    FOOD_UPDATE_SUCCESS(HttpStatus.OK, "음식 수정에 성공하였습니다."),
    FOOD_DELETE_SUCCESS(HttpStatus.OK, "음식이 삭제되었습니다."),
    // 주문
    ORDER_SUCCESS(HttpStatus.OK, "주문에 성공하였습니다."),
    ORDER_CHECK_SUCCESS(HttpStatus.OK, "주문 조회에 성공하였습니다."),
    // 댓글
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "댓글 등록에 성공하였습니다."),
    COMMENT_CHECK_SUCCESS(HttpStatus.OK, "댓글 조회에 성공하였습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글 수정에 성공하였습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글이 삭제되었습니다."),
    //좋아요
    LIKE_CREATE_SUCCESS(HttpStatus.OK, "좋아요가 등록 되었습니다."),
    LIKE_DELETE_SUCCESS(HttpStatus.OK, "좋아요가 취소 되었습니다."),
    LIKE_SELECT_SUCCESS(HttpStatus.OK, "좋아요 조회 성공,,,"),
    //팔로우
    FOLLOW_CREATE_SUCCESS(HttpStatus.OK, "팔로우 성공"),
    FOLLOWING_GET_POSTS_SUCCESS(HttpStatus.OK, "팔로잉한 사용자의 게시글 조회에 성공하였습니다"),

    // [ADMIN]
    // 공지
    ANNOUNCEMENT_POST_CREATE_SUCCESS(HttpStatus.OK, "공지 등록에 성공하였습니다."),

    // 음식점
    RESTAURANT_PINNED_SUCCESS(HttpStatus.OK, "해당 음식점을 상단에 고정하였습니다."),

    // 음식점
    COMMENTS_CHECK_SUCCESS(HttpStatus.OK, "전체 댓글 조회에 성공하였습니다."),

    // 프로필
    PROFILES_CHECK_SUCCESS(HttpStatus.OK, "전체 사용자를 조회합니다."),

    // 권한
    USERTYPE_CHANGE_SUCCESS(HttpStatus.OK, "권한이 수정되었습니다."),


    ;
    private final HttpStatus httpStatus;
    private final String message;
}
