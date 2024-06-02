package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record EventParticipantRequest(
        String statusRegistration,
        CustomerRequest customer,
        CreditCardRequest card
) {
}