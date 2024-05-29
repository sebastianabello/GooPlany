package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.Gender;
import com.gooplanycol.gooplany.utils.Level;
import com.gooplanycol.gooplany.utils.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends User {
    private Long id;
    private String cellphone;
    private String email;
    private List<Token> tokens;
    private List<ConfirmationToken> confirmationTokens;
    private LocalDate createAt;
    private LocalDate updatedAt;
    private String firstName;
    private String lastName;
    private boolean enable;
    private String username;
    private String pwd;
    private LocalDate birthdate;
    private String country;
    private String description;
    private String emergencyContact;
    private Gender gender;
    private Level level;
    private Media profilePicture;
    private Media headerImage;
    private List<Role> roles;
}
