package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Profile {
    private Long id;
    private String name;
    private String cellphone;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
