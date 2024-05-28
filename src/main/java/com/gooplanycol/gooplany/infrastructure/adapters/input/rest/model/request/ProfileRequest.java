package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

public record ProfileRequest(
        String cellphone,
        String email,
        String username,
        String pwd,
        String firstName,
        String lastName,
        LocalDate birthdate,
        String country,
        LocalDate createAt
) {
}
