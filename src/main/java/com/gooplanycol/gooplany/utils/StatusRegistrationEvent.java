package com.gooplanycol.gooplany.utils;

public enum StatusRegistrationEvent {
    REGISTERED,
    CANCELED,
    UNREGISTERED;

    private String message;

    StatusRegistrationEvent() {
        this.message = this.name().toLowerCase();
    }
}
