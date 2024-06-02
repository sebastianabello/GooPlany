package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String cellphone,
        LocalDateTime createdAt
) {
}
