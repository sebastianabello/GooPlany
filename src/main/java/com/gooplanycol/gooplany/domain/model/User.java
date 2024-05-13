package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}
