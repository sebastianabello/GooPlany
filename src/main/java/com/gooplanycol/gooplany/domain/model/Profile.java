package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.Country;
import com.gooplanycol.gooplany.utils.Gender;
import com.gooplanycol.gooplany.utils.Level;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate birthdate;
    private Country country;
    private String description;
    private String emergencyContact;
    private Gender gender;
    private Level level;
    private User user;
    private Media profilePicture;
    private Media headerImage;
}
