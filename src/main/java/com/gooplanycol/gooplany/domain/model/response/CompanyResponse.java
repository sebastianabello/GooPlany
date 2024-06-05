package com.gooplanycol.gooplany.domain.model.response;


public record CompanyResponse (
        Long id,
        String name,
        String nit,
        String cellphone,
        String email,
        String username
) {
}
