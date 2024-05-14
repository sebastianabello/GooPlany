package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.User;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateRequest {
    private User user;
    private String name;
    private String phoneNumber;
    private Address address;
}
