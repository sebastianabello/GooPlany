package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

public record CompanyResponse(
        Long id,
        String name,
        String cellphone,
        String email,
        String username
) {
}
