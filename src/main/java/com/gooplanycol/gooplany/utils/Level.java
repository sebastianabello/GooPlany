package com.gooplanycol.gooplany.utils;

// Esta tabla sirve para guardar los niveles de los usuarios por ejemplo privado, publico, etc.
public enum Level {
        PRIVATE,
        PUBLIC,
        FRIENDS,
        FRIENDS_OF_FRIENDS;

        private String message;

        Level() {
                this.message = this.name().toLowerCase();
        }
}
