package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.gooplanycol.gooplany.domain.model.User;
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
public class ProfileResponse {
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
}
