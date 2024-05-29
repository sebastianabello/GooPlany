package com.gooplanycol.gooplany.utils;

public enum EventCategory {
    NOT_SPECIFIED,
    MUSIC,
    THEATER,
    SPORTS,
    EXHIBITION,
    CONFERENCE,
    WORKSHOP,
    PARTY,
    FESTIVAL,
    CULTURAL,
    RELIGIOUS,
    CHARITY,
    OTHER;

    private String message;

    EventCategory() {
        this.message = this.name().toLowerCase();
    }

}
