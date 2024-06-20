package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.entity.User;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private final String accountId;
    @Getter(value = AccessLevel.PRIVATE)
    private final String password;
    private final String name;
    private final String intro;
    private final String role;

    public ProfileResponseDto(User user) {
        this.accountId = user.getAccountId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.intro = user.getIntro();
        this.role = String.valueOf(user.getUserType());
    }

}
