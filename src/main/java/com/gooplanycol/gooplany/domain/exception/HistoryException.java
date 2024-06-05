package com.gooplanycol.gooplany.domain.exception;

public class HistoryException extends RuntimeException {
    public HistoryException(String message) {
        super("HISTORY_EXCEPTION: " + message);
    }

    public HistoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
