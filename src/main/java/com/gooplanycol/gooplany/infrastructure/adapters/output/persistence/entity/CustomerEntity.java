package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.Gender;
import com.gooplanycol.gooplany.utils.Level;
import com.gooplanycol.gooplany.utils.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity extends UserEntity implements UserDetails {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "history_id")
    private HistoryCustomerEntity historyCompany;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<AddressEntity> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<CreditCardEntity> cards;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.PERSIST)
    private List<TokenEntity> tokens;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<ConfirmationTokenCustomerEntity> confirmationTokens;

    private String lastName;
    private boolean enable;
    @Column(unique = true)
    private String username;
    private String pwd;
    private LocalDate birthdate;
    private String country;
    @Column(length = 120)
    private String description;
    private String emergencyContact;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Level level;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "customer_roles",
            joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
