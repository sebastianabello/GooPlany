package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.TypeCard;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    private Long id;
    private String holderName;
    private String number;
    private int monthExp;
    private int yearExp;
    private int cvv;
    private TypeCard typeCard;
}
