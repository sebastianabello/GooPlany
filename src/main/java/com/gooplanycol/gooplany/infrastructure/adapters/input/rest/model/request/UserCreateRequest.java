package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
