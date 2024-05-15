package com.gooplanycol.gooplany.domain.exception;

public class AlreadyRegisteredException extends RuntimeException{
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
