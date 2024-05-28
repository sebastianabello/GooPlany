package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;


import java.time.LocalDateTime;

public record EventRegistrationResponse(
        Long id,
        String statusRegistrationEvent,
        LocalDateTime registeredAt,
        Long profileId,
        Long eventId
) {
}
