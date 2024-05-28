package com.gooplanycol.gooplany.domain.exception;

public class ProfileException extends RuntimeException {

    public ProfileException(String message) {
        super("PROFILE_EXCEPTION: " + message);
    }

    public ProfileException(String message, Throwable cause) {
        super(message, cause);
    }

}
