package com.sparta.mat_dil.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
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
    // 댓글
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "댓글 등록에 성공하였습니다."),
    COMMENTS_CHECK_SUCCESS(HttpStatus.OK, "댓글 조회에 성공하였습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글 수정에 성공하였습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글이 삭제되었습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
