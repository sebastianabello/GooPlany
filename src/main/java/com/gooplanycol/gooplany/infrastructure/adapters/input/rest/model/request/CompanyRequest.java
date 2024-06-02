package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDateTime;
import java.util.List;

public record CompanyRequest(
        String name,
        String cellphone,
        String email,
        String username,
        String pwd,
        LocalDateTime createdAt,
        List<AddressRequest> address
) {
}
