package com.gooplanycol.gooplany.domain.model.request;

import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;

import java.time.LocalDateTime;

public record EventParticipantRequest(
        String statusRegistration,
        LocalDateTime createAt,
        CustomerResponse customer,
        CreditCardResponse card
) {
}