package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class CompanyEntity extends ProfileEntity implements UserDetails {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "history_id")
    private HistoryEntity history;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private List<AddressEntity> address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<TokenCompanyEntity> tokens;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<ConfirmationTokenCompanyEntity> confirmationTokens;

    private boolean enabled;

    @Column(unique = true)
    private String username;

    private String pwd;

    @Column(unique = true)
    private String nit;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "company_roles",
            joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.name())).toList();
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return username;
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
        return enabled;
    }
}
