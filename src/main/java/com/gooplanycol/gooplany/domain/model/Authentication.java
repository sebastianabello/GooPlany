package com.gooplanycol.gooplany.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
    private String token;
}
