package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record CompanyRequestEdit(
        String name,
        String cellphone,
        String nit,
        String email,
        String username
) {
}
