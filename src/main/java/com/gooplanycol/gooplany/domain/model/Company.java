package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Profile {
    private HistoryCompany historyCompany;
    private List<Address> address;
    private List<Token> tokens;
    private List<ConfirmationToken> confirmationTokens;
    private boolean enable;
    private String username;
    private String pwd;
    private Media companyPicture;
    private Media headerImage;
    private List<Role> roles;

    public Company(String jwtToken) {
        String token = jwtToken;
    }
}
