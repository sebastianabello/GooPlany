package com.gooplanycol.gooplany.domain.exception;

public class EventPostException extends RuntimeException {

    public EventPostException(String message) {
        super("EVENT_POST_EXCEPTION: " + message);
    }

    public EventPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
