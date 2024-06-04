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
public class TokenCustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tok;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private boolean revoked;
    private boolean expired;

}
