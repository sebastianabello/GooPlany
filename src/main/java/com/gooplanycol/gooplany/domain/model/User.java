package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private Role role;
    private UserState state;
}
