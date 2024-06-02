package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestEdit {
    private String name;
    private String cellphone;
    private String nit;
    private String email;
    private String username;
}
