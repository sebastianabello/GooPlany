package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;
import java.util.List;

public record CustomerRequest(
        String cellphone,
        String email,
        String username,
        String pwd,
        String firstName,
        String lastName,
        LocalDate birthdate,
        String country,
        List<AddressRequest> address,
        LocalDate createAt
) {
}
