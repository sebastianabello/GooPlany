package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String cellphone;
    private LocalDateTime createdAt;
    private String description;
    private String emergencyContact;
    private String gender;
    private String level;
}
