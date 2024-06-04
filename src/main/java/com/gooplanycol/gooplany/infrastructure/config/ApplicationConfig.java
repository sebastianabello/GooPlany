package com.gooplanycol.gooplany.infrastructure.config;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var company = companyRepository.findCompanyByUsername(username);
            if (company.isPresent()) {
                return company.get();
            } else {
                var customer = customerRepository.findCustomerByUsername(username);
                if (customer.isPresent()) {
                    return customer.get();
                } else {
                    throw new UsernameNotFoundException("User not found");
                }
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
