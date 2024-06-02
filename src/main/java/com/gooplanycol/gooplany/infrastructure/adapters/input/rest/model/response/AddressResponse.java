package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;
    private String street;
    private String country;
    private String postalCode;
}
