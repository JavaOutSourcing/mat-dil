package com.sparta.mat_dil.dto;

import com.sparta.mat_dil.enums.ResponseStatus;

public class OrderDetailData<T> {
    private int status;
    private String message;
    private int totalPrice;
    private T data;

    public OrderDetailData(ResponseStatus responseStatus, T data, int totalPrice) {
        this.status = responseStatus.getHttpStatus().value();
        this.message = responseStatus.getMessage();
        this.totalPrice = totalPrice;
        this.data = data;
    }
}
