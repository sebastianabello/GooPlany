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
    private Profile profile;
    private Company company;
    private boolean revoked;
    private boolean expired;

    public Token(Long id, String token, TokenType tokenType, Company company, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.company = company;
        this.revoked = revoked;
        this.expired = expired;
    }

    public Token(Long id, String token, TokenType tokenType, Profile profile, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.profile = profile;
        this.revoked = revoked;
        this.expired = expired;
    }

}
