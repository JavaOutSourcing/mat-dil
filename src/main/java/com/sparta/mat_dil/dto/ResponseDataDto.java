package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.enums.ResponseStatus;
import lombok.Getter;

@Getter
public class ResponseDataDto<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDataDto(ResponseStatus responseStatus, T data) {
        this.status = responseStatus.getHttpStatus().value();
        this.message = responseStatus.getMessage();
        this.data = data;
    }

}
