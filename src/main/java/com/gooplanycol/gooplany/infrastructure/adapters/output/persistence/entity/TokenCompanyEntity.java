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
public class TokenCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tok;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    private boolean revoked;
    private boolean expired;

}
