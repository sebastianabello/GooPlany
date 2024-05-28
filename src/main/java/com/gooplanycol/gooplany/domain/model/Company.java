package com.gooplanycol.gooplany.domain.model;
import com.gooplanycol.gooplany.utils.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company extends User {
    private String name;
    private String nit;
    private boolean enable;
    private String pwd;
    private Address address;
    private List<Role> roles;
}
