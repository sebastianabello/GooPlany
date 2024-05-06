package com.gooplanycol.gooplany.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    USER_INVALID("USER_INVALID", "User invalid"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", "User not active"),
    USER_NOT_ADMIN("USER_NOT_ADMIN", "User not admin"),
    USER_NOT_AUTHORIZED("USER_NOT_AUTHORIZED", "User not authorized"),
    USER_NOT_AUTHENTICATED("USER_NOT_AUTHENTICATED", "User not authenticated"),
    USER_NOT_VALID("USER_NOT_VALID", "User not valid"),
    USER_NOT_DELETED("USER_NOT_DELETED", "User not deleted"),
    USER_NOT_UPDATED("USER_NOT_UPDATED", "User not updated"),
    USER_NOT_SAVED("USER_NOT_SAVED", "User not saved"),
    USER_NOT_CREATED("USER_NOT_CREATED", "User not created"),
    USER_NOT_RETRIEVED("USER_NOT_RETRIEVED", "User not retrieved"),
    USER_NOT_LISTED("USER_NOT_LISTED", "User not listed"),
    USER_NOT_FOUND_BY_ID("USER_NOT_FOUND_BY_ID", "User not found by id"),
    GENERIC_ERROR("GENERIC_ERROR", "Generic error");


    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
