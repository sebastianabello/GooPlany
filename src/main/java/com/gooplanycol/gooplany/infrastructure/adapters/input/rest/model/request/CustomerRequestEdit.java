package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record CustomerRequestEdit(
        String name,
        String lastName,
        String cellphone,
        String email,
        String username,
        String description,
        String emergencyContact,
        String gender,
        String level
) {
}
