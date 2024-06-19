package com.sparta.mat_dil.exception;

import com.sparta.mat_dil.enums.ErrorType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String result;
    private ErrorType errorType;

    public CustomException(ErrorType errorType) {
        this.result = "ERROR";
        this.errorType = errorType;
    }
}