package com.gooplanycol.gooplany.utils;

public enum StatusEventPost {
    PENDING,
    APPROVED,
    REJECTED,
    FINISHED;

    private String message;

    StatusEventPost() {
        this.message = this.name().toLowerCase();
    }
}
