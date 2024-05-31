package com.gooplanycol.gooplany.utils;

public enum StatusEventParticipant {
    REGISTERED,
    CANCELED,
    UNREGISTERED;

    private String message;

    StatusEventParticipant() {
        this.message = this.name().toLowerCase();
    }
}
