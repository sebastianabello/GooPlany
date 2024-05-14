package com.gooplanycol.gooplany.domain.model;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private User user;
    private Long id;
    private String name;
    private String phoneNumber;
    private Address address;
}
