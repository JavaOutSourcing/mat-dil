package com.sparta.mat_dil.entity;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN("ADMIN"),
    CONSUMER("CONSUMER"),
    SUPPLIER("SUPPLIER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

}
