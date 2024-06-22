package com.sparta.mat_dil.enums;

public enum ContentTypeEnum {
    RESTAURANT("restaurant"),
    COMMENT("comment");

    private final String contentType;

    ContentTypeEnum(String value) {
        this.contentType = value;
    }
}
