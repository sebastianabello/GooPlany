package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public abstract class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cellphone;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST)
    private List<TokenEntity> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<ConfirmationTokenEntity> confirmationTokens;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

}