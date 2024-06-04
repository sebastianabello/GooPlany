package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String cellphone;
    @Column(unique = true)
    private String email;
}