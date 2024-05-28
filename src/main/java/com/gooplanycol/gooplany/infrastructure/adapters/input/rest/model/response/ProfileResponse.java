package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDateTime;

public record ProfileResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String cellphone,
        LocalDateTime createdAt
) {
}
