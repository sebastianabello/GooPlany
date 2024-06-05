package com.gooplanycol.gooplany.domain.exception;

public class CompanyException extends RuntimeException {

    public CompanyException(String message) {
        super("COMPANY_EXCEPTION: " + message);
    }

    public CompanyException(String message, Throwable cause) {
        super(message, cause);
    }

}
