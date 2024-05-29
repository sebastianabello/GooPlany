package com.gooplanycol.gooplany.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
    String username;
    String password;
    String token;
}
