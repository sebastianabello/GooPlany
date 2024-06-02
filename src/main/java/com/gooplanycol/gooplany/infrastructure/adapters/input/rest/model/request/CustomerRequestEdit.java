package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestEdit {
    private String name;
    private String lastName;
    private String cellphone;
    private String email;
    private String username;
    private String description;
    private String emergencyContact;
    private String gender;
    private String level;
}
