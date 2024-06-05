package com.gooplanycol.gooplany.domain.model.response;


public record CreditCardResponse(
        Long id,
        String number,
        String typeCard
) {
}