package com.gooplanycol.gooplany.domain.exception;

public class EventStokeException extends RuntimeException {

    public EventStokeException(String message) {
        super("EVENT_STOKE_EXCEPTION: " + message);
    }

    public EventStokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
