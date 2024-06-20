package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.enums.ResponseStatus;
import lombok.Getter;

@Getter
public class ResponseMessageDto {
    private int status;
    private String message;

    public ResponseMessageDto(ResponseStatus status) {
        this.status = status.getHttpStatus().value();
        this.message = status.getMessage();
    }
}
