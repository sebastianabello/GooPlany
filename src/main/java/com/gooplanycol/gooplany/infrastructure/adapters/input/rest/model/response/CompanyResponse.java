package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.User;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private User user;
    private Long id;
    private String name;
    private String phoneNumber;
    private Address address;
}
