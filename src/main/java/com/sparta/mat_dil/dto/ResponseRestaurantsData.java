package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.enums.ResponseStatus;

public class ResponseRestaurantsData <T> {
    private String status;
    private String message;
    private int totalPrice;
    private T data;

    public ResponseRestaurantsData(ResponseStatus responseStatus, T data) {
        this.status = responseStatus.getHttpStatus().toString();
        this.message = responseStatus.getMessage();
        this.data = data;
    }
}
