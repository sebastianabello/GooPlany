package com.gooplanycol.gooplany.domain.model.request;

public record CustomerRequestEdit(
        String name,
        String lastName,
        String cellphone,
        String email,
        String username
) {

}
