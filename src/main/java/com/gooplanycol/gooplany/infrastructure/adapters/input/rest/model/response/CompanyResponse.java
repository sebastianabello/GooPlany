package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

public record CompanyResponse(
        Long id,
        String name,
        String nit,
        String cellphone,
        String email,
        String username
) {
}
