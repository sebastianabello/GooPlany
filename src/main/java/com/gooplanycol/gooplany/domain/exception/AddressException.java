package com.gooplanycol.gooplany.domain.exception;

public class AddressException extends RuntimeException {
    public AddressException(String message) {
        super("ADDRESS_EXCEPTION: " + message);
    }

    public AddressException(String message, Throwable cause) {
        super(message, cause);
    }
}
