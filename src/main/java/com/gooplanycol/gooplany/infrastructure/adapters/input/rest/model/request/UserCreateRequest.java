package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Email is invalid")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
    @NotNull(message = "Active status is required")
    private Boolean isActive;
    @NotNull(message = "Admin status is required")
    private Boolean isAdmin;
}
