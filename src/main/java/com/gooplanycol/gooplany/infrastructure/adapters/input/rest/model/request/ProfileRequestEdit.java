package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

public record ProfileRequestEdit(
        String cellphone,
        String email,
        String username,
        String firstName,
        String lastName,
        String description,
        String emergencyContact,
        String level,
        LocalDate updatedAt
) {
}
