package com.gooplanycol.gooplany.domain.model.request;

public record CompanyRequest (
        String name,
        String nit,
        String cellphone,
        String email,
        String username,
        String pwd,
        AddressRequest address
) {
}
