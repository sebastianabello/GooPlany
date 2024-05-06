package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dayOfBirth;
    private String phone;
    private String code;
    private String password;
    private Boolean isActive;
    private Boolean isAdmin;
    private LocalDateTime createdAt;

}
