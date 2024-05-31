package com.gooplanycol.gooplany.utils;

public enum TypeCard {
    VISA,
    MASTER_CARD,
    AMERICAN_EXPRESS;

    private String message;

    TypeCard() {
        this.message = this.name().toLowerCase();
    }

}
