package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipantRequest {
    private String statusRegistration;
    private CustomerRequest customer;
    private CreditCardRequest card;
}