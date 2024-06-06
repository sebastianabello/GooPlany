package com.gooplanycol.gooplany.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.gooplanycol.gooplany.utils.Role.ADMIN;
import static com.gooplanycol.gooplany.utils.Role.CUSTOMER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutService logoutService;

    private static final String[] adminUrls =
            {
                    "/api/v1/customer/{id}/remove",
                    "/api/v1/customer/find",
                    "/api/v1/customer/find/email/{email}",
                    "/api/v1/address/**",
                    "/api/v1/card/**",
                    "api/v1/history/{id}/remove",
                    "api/v1/history/find/{id}",
                    "api/v1/history/find",
                    "api/v1/history/{id}/find/eventFinished",
                    "api/v1/history/{id}/add/eventFinished",
                    "api/v1/history/{historyId}/remove/eventFinished/{eventFinishedId}",
                    "api/v1/eventPost/**",
                    "/api/v1/eventStock/save",
                    "/api/v1/eventStock/{id}/remove",
                    "/api/v1/eventStock/{id}/edit",
                    "/api/v1/eventStock/find/title/{title}",
                    "/api/v1/eventFinished/{id}/edit",
                    "/api/v1/eventFinished/find/{id}",
                    "/api/v1/eventFinished/find",
                    "/api/v1/eventFinished/{id}/add/EventParticipant",
                    "/api/v1/eventFinished/{eventFinishedId}/remove/eventParticipant/{eventParticipantId}",
                    "/api/v1/eventFinished/{id}/find/eventParticipants",
                    "/api/v1/eventFinished/find/{id}/eventPost",
                    "/api/v1/eventParticipant/edit/{id}",
                    "/api/v1/eventParticipant/change/status/{status}/{id}",
                    "/api/v1/eventParticipant/find",
                    "/api/v1/eventParticipant/remove/{id}",
                    "/api/v1/eventParticipant/find/status/{status}",
                    "/api/v1/eventParticipant/find/{id}/customer",
                    "/api/v1/eventParticipant/find/{id}/card"};
    private static final String[] customersUrls =
            {
                    "/api/v1/customer/find/{id}",
                    "/api/v1/customer/{id}/edit",
                    "/api/v1/customer/{id}/add/address",
                    "/api/v1/customer/{customerId}/remove/address/{addressId}",
                    "/api/v1/customer/{id}/add/card",
                    "/api/v1/customer/{customerId}/remove/card/{cardId}",
                    "/api/v1/customer/find/{id}/history",
                    "/api/v1/customer/find/{id}/address",
                    "/api/v1/customer/find/{id}/cards",
                    "/api/v1/customer/{id}/change/pwd/{pwd}",
                    "/api/v1/customer/find/by/tk/{token}",
                    "/api/v1/history/{id}/add/eventFinished",
                    "/api/v1/eventFinished/save",
                    "/api/v1/eventStock/find/{id}",
                    "/api/v1/eventStock/find/enable",
                    "/api/v1/eventStock/{id}/participate/{amount}",
                    "/api/v1/eventParticipant/save",
                    "/api/v1/eventParticipant/find/id/{id}"
            };
    private static final String[] openUrls =
            {
                    "/api/v1/authentication/**",
                    "/api/v1/eventStock/find",
                    "/api/v1/eventStock/find/{offset}/{pageSize}",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
            };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                //.cors(AbstractHttpConfigurer::disable)// to get access from different url
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(openUrls).permitAll()
                        .requestMatchers(adminUrls).hasRole(ADMIN.name())
                        .requestMatchers(customersUrls).hasAnyRole(CUSTOMER.name(), ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler(
                                (request, response, authentication) ->
                                        SecurityContextHolder.clearContext()))
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();

    }

}
