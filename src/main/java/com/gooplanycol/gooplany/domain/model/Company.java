package com.gooplanycol.gooplany.domain.model;
import com.gooplanycol.gooplany.utils.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company extends User {
    private Long id;
    private String cellphone;
    private String email;
    private List<Token> tokens;
    private List<ConfirmationToken> confirmationTokens;
    private LocalDate createAt;
    private LocalDate updatedAt;
    private String name;
    private String nit;
    private boolean enable;
    private String pwd;
    private History history;
    private List<Address> address;
    private List<Role> roles;

}
