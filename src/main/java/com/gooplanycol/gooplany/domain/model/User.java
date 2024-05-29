package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String cellphone;
    private String email;
    private List<Token> tokens;
    private List<ConfirmationToken> confirmationTokens;
    private LocalDate createAt;
    private LocalDate updatedAt;
}
