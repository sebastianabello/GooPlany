package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

public record CreditCardResponse(
        Long id,
        String number,
        String type
) {
}
