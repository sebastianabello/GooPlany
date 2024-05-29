package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private User user;


    /**
    public ConfirmationToken() {
        this.profile = new Profile();
    }
     */

}

