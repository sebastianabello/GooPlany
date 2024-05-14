package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.Country;
import com.gooplanycol.gooplany.utils.Gender;
import com.gooplanycol.gooplany.utils.Level;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private UUID id;
    private User user;
    private String firstName;
    private String lastName;
    private String userName;
    private LocalDate birthdate;
    private String description;
    private Country country;
    private Address address;
    private EmergencyContact emergencyContact;
    private Level level;
    private Media media;
    private Gender gender;
    private List<Interest> interests;
}
