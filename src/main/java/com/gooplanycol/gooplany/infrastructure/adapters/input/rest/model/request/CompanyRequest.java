package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

public record CompanyRequest(
        String name,
        String cellphone,
        String email,
        String nit,
        String pwd,
        AddressRequest address,
        LocalDate createAt
) {
}
