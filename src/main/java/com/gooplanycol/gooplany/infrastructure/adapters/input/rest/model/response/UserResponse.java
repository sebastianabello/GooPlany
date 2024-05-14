package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private LocalDateTime createAt;
}
