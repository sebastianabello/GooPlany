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
public class Customer extends Profile {
    protected Long id;
    protected String name;
    protected String cellphone;
    protected String email;
    protected LocalDateTime createAt;
    protected LocalDateTime updatedAt;
    private HistoryCustomer historyCustomer;
    private List<Address> address;
    private List<CreditCard> cards;
    private List<Token> tokens;
    private List<ConfirmationToken> confirmationTokens;
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


    private Level findLevel(String level){
        return switch (level) {
            case "private" -> Level.PRIVATE;
            case "friends" -> Level.FRIENDS;
            case "friends_of_friends" -> Level.FRIENDS_OF_FRIENDS;
            default -> Level.PUBLIC;
        };
    }


}
