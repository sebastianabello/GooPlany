package com.gooplanycol.gooplany.domain.exception;

public class EventFinishedException extends RuntimeException {
    public EventFinishedException(String message) {
        super("EVENT_FINISHED_EXCEPTION: " + message);
    }

    public EventFinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
