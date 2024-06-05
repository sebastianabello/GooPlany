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
public class Company extends Profile implements UserDetails {
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "history_id")
    private History history;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private List<Address> address;
    @OneToMany(mappedBy = "company",cascade = CascadeType.PERSIST)
    private List<Token> tokens;
    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<ConfirmationToken> confirmationTokens;
    private boolean enable;
    @Column(unique = true)
    private String nit;
    private String username;
    private String pwd;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "company_roles",
            joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<? extends GrantedAuthority> collect = roles.stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_"+role.name());
        }).collect(Collectors.toList());
        return collect;
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
