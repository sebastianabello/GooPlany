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
    private String number;
    private TypeCard typeCard;
}
