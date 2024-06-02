package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CustomerRequest(
        String name,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        String cellphone,
        String email,
        String username,
        String pwd,
        AddressRequest address
) {
}
