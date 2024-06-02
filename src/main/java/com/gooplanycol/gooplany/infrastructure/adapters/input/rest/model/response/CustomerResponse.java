package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String lastName,
        String username,
        String email,
        String cellphone,
        LocalDateTime createdAt,
        String description,
        String emergencyContact,
        String gender,
        String level
) {
}
