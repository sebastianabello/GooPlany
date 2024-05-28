package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

public record CompanyRequestEdit(
        String name,
        String cellphone,
        String email,
        LocalDate updatedAt
) {
}
