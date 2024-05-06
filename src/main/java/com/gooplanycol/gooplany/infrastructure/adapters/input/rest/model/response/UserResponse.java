package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dayOfBirth;
    private String phone;
    private Boolean isActive;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
}
