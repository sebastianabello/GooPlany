package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardResponse {
    private Long id;
    private String number;
    private String type;
}