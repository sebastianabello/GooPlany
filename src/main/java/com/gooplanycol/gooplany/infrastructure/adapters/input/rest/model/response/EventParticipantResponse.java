package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EventParticipantResponse(
        Long id,
        String statusRegistration,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt,
        Long customerId,
        Long card
) {
}