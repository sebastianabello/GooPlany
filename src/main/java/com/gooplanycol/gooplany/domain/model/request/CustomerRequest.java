package com.gooplanycol.gooplany.domain.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CustomerRequest(
        String name,
        String lastName,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthdate,
        String cellphone,
        String email,
        String username,
        String pwd,
        AddressRequest address
) {
}
