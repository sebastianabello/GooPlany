package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.TokenType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private boolean revoked;

    private boolean expired;

    public Token(Long id, String token, TokenType tokenType, Customer customer, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.customer = customer;
        this.revoked = revoked;
        this.expired = expired;
    }

    public Token(Long id, String token, TokenType tokenType, Company company, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.company = company;
        this.revoked = revoked;
        this.expired = expired;
    }
}
