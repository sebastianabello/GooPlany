package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

public record AddressResponse (
        Long id,
        String street,
        String country,
        String postalCode
) {
}
