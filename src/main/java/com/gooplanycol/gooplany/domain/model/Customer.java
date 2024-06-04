package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.Gender;
import com.gooplanycol.gooplany.utils.Level;
import com.gooplanycol.gooplany.utils.Role;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private String cellphone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private History history;
    private List<Address> address;
    private List<CreditCard> cards;
    private List<TokenCustomer> tokens;
    private List<ConfirmationTokenCustomer> confirmationTokens;
    private boolean enabled;
    private String username;
    private String pwd;
    private String lastName;
    private LocalDate birthdate;
    private String description;
    private String emergencyContact;
    private Gender gender;
    private Level level;
    private List<Role> roles;

    private String token;
    public Customer(String token) {
        this.token = token;
    }

    public Gender findGender(String gender) {
        return switch (gender) {
            case "male" -> Gender.MALE;
            case "female" -> Gender.FEMALE;
            case "non_binary" -> Gender.NON_BINARY;
            default -> Gender.PREFER_NOT_TO_SAY;
        };
    }

    public Level findLevel(String level){
        return switch (level) {
            case "private" -> Level.PRIVATE;
            case "friends" -> Level.FRIENDS;
            case "friends_of_friends" -> Level.FRIENDS_OF_FRIENDS;
            default -> Level.PUBLIC;
        };
    }
}
