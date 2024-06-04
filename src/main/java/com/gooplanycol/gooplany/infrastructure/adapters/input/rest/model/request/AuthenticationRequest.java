package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String pwd;
}
