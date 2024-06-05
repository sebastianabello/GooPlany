package com.gooplanycol.gooplany.domain.model.response;

import java.time.LocalDateTime;

public record EventParticipantResponse(
        Long id,
        String statusRegistration,
        LocalDateTime registeredAt,
        Long customerId,
        Long creditCardId
) {
}
