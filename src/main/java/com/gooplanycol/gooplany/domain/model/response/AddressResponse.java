package com.gooplanycol.gooplany.domain.model.response;

public record AddressResponse(
        Long id,
        String street,
        String country,
        String postalCode
) {
}
