package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record CreditCardRequest(
        String number,
        String type
) {
}
