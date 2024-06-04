package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.Role;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long id;
    private String name;
    private String cellphone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private History history;
    private List<Address> address;
    private List<TokenCompany> tokens;
    private List<ConfirmationTokenCompany> confirmationTokens;
    private boolean enabled;
    private String nit;
    private String username;
    private String pwd;
    private List<Role> roles;

    private String tok;

    public Company(String tok) {
        this.tok = tok;
    }
}
