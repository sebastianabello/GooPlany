package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String nit;
    private String cellphone;
    private String email;
    private String username;
}
