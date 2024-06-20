package com.sparta.mat_dil.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ProfileRequestDto {
    private String password;

    @Length(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$",
            message = "영어, 숫자 및 특수문자 조합만 가능합니다.")
    private String newPassword;

    private String name;
    private String intro;
}