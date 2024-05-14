package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.gooplanycol.gooplany.utils.Country;
import com.gooplanycol.gooplany.utils.TypeOfStreet;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private Country country;
    private String department;
    private String neighborhood;
    private String streetName;
    private String streetNumber;
    private TypeOfStreet typeOfStreet;
    private String complement;
}
