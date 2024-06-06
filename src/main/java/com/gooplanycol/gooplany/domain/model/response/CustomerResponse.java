package com.gooplanycol.gooplany.domain.model.response;

public record CustomerResponse(
        Long id,
        String name,
        String lastname,
        String cellphone,
        String email,
        String username
) {
}
