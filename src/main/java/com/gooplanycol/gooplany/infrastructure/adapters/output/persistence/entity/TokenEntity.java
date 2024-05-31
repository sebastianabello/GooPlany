package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tok;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private CustomerEntity profile;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    private boolean revoked;

    private boolean expired;

    public TokenEntity(Long id, String tok, TokenType tokenType, CompanyEntity company, boolean revoked, boolean expired) {
        this.id = id;
        this.tok = tok;
        this.tokenType = tokenType;
        this.company = company;
        this.revoked = revoked;
        this.expired = expired;
    }

    public TokenEntity(Long id, String tok, TokenType tokenType, CustomerEntity profile, boolean revoked, boolean expired) {
        this.id = id;
        this.tok = tok;
        this.tokenType = tokenType;
        this.profile = profile;
        this.revoked = revoked;
        this.expired = expired;
    }

}
