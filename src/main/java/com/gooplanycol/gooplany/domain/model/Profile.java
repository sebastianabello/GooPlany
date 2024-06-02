package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Profile {
    protected Long id;
    protected String name;
    protected String cellphone;
    protected String email;
    protected LocalDateTime createAt;
    protected LocalDateTime updatedAt;
}
