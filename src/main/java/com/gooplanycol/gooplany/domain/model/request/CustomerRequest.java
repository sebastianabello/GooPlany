package com.gooplanycol.gooplany.domain.model.request;

public record CustomerRequest(
        String name,
        String lastName,
        String cellphone,
        String email,
        String username,
        String pwd,
        AddressRequest address
) {
}
