package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record AddressRequest(
        String street,
        String country,
        String postalCode
) {
}