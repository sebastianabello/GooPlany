package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.TokenType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private Long id;
    private String token;
    private TokenType tokenType;
    private User user;
    private boolean revoked;
    private boolean expired;
}
