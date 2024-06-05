package com.gooplanycol.gooplany.domain.model.request;

public record CompanyRequestEdit(
        String name,
        String cellphone,
        String email,
        String username
) {
}
