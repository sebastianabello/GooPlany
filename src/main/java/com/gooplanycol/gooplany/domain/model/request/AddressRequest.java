package com.gooplanycol.gooplany.domain.model.request;

public record AddressRequest(
        String street,
        String country,
        String postalCode
) {
}