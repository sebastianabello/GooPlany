package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

public record CustomerRequest(
        String name,
        String lastName,
        LocalDate birthdate,
        String cellphone,
        String email,
        String username,
        String pwd,
        AddressRequest address
) {
}
