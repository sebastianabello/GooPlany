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

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    private boolean revoked;

    private boolean expired;

    public TokenEntity(Long id, String token, TokenType tokenType, CompanyEntity company, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.company = company;
        this.revoked = revoked;
        this.expired = expired;
    }

    public TokenEntity(Long id, String token, TokenType tokenType, ProfileEntity profile, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.profile = profile;
        this.revoked = revoked;
        this.expired = expired;
    }

}
