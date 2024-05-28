package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.gooplanycol.gooplany.utils.Gender;
import com.gooplanycol.gooplany.utils.Level;

import java.time.LocalDate;

public record ProfileRequestEdit(
        String cellphone,
        String email,
        String username,
        String firstName,
        String lastName,
        String description,
        String emergencyContact,
        Gender gender,
        Level level,
        LocalDate updatedAt
) {
}
