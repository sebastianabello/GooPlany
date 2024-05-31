package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDateTime;

public record CompanyRequest(
        String name,
        String cellphone,
        String email,
        String username,
        String pwd,
        LocalDateTime createdAt,
        AddressRequest address
) {
}
