package com.sparta.mat_dil.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // user
    DUPLICATE_ACCOUNT_ID(HttpStatus.LOCKED, "이미 아이디가 존재합니다."),
    DUPLICATE_EMAIL(HttpStatus.LOCKED, "이미 존재하는 이메일입니다."),
    INVALID_ACCOUNT_ID(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL(HttpStatus.LOCKED, "이메일을 잘못 입력하였습니다."),
    DEACTIVATE_USER(HttpStatus.FORBIDDEN, "이미 탈퇴한 회원입니다."),
    BLOCKED_USER(HttpStatus.LOCKED, "차단된 회원입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_ACCEPTED_COMMENT(HttpStatus.LOCKED, "본인의 음식점에는 댓글들 작성할 수 없습니다."),
    CONTENT_OWNER(HttpStatus.NOT_FOUND, "본인의 음식점과 댓글에는 ‘좋아요' 또는 '팔로우'를 할 수 없습니다."),
    PASSWORD_RECENTLY_USED(HttpStatus.LOCKED, "최근에 사용한 비밀번호는 사용할 수 없습니다."),

    // restaurant,
    NOT_FOUND_RESTAURANT(HttpStatus.NOT_FOUND, "존재하지 않는 음식점입니다."),

    //food
    NOT_FOUND_FOOD(HttpStatus.NOT_FOUND, "존재하지 않는 음식입니다."),

    // comment,
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    NO_ATUTHENTIFICATION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),


    // JWT
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다. 다시 로그인 해주세요."),
    LOGGED_OUT_TOKEN(HttpStatus.FORBIDDEN, "이미 로그아웃된 토큰입니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    EXPIRED_JWT(HttpStatus.FORBIDDEN, "만료된 JWT 입니다."),

    REQUIRES_LOGIN(HttpStatus.LOCKED, "로그인이 필요한 서비스입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
