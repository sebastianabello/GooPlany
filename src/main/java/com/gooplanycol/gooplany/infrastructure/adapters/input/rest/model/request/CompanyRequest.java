package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record CompanyRequest(
        String name,
        String cellphone,
        String nit,
        String email,
        String username,
        String pwd,
        AddressRequest address
) {
}
