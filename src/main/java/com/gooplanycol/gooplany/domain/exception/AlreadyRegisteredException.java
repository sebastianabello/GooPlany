package com.gooplanycol.gooplany.domain.exception;

public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException(String message) {
        super("ALREADY_REGISTER_EXCEPTION: " + message);
    }

    public AlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }


}
