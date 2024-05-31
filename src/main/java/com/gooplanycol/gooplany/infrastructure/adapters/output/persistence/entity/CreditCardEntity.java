package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.TypeCard;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String holderName;
    private String number;
    private int monthExp;
    private int yearExp;
    private int cvv;
    @Enumerated(EnumType.STRING)
    private TypeCard typeCard;
}
