package com.gooplanycol.gooplany.utils;

// Esta tabla sirve para guardar los tipos de audiencia de los eventos, ejemplo, menor de edad, adulto, etc.
public enum TypeOfAudience {
    NOT_SPECIFIED,
    CHILDREN,
    TEENAGERS,
    ADULTS,
    ELDERLY,
    FAMILY,
    STUDENTS,
    PROFESSIONALS,
    TOURISTS,
    LOCALS,
    FOREIGNERS;

    private String message;

    TypeOfAudience() {
        this.message = this.name().toLowerCase();
    }
}
