package com.gooplanycol.gooplany.domain.exception;

public class EvenFinishedException extends RuntimeException {
    public EvenFinishedException(String message) {
        super("EVENT_FINISHED_EXCEPTION: " + message);
    }

    public EvenFinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
