package com.gooplanycol.gooplany.domain.model.response;

import java.time.LocalDateTime;

public record EventParticipantResponse(
        Long id,
        String statusRegistration,
        LocalDateTime createAt,
        Long customerId,
        Long creditCardId
) {
}
