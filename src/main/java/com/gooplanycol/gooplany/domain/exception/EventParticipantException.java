package com.gooplanycol.gooplany.domain.exception;

public class EventParticipantException extends RuntimeException {
    public EventParticipantException(String message) {
        super("EVENT_PARTICIPANT_EXCEPTION: " + message);
    }

    public EventParticipantException(String message, Throwable cause) {
        super(message, cause);
    }
}
