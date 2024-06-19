package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.enums.ResponseStatus;

public class ResponseDataDto<T> {
    private String status;
    private String message;
    private T data;

    public ResponseDataDto(ResponseStatus responseStatus, T data) {
        this.status = responseStatus.getHttpStatus().toString();
        this.message = responseStatus.getMessage();
        this.data = data;
    }
}
