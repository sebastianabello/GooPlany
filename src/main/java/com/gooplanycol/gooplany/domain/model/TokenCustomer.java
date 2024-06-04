package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.TokenType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenCustomer {
    private Long id;
    private String tok;
    private TokenType tokenType;
    private Customer customer;
    private boolean revoked;
    private boolean expired;
}
