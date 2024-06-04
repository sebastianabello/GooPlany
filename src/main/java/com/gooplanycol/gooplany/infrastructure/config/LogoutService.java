package com.gooplanycol.gooplany.infrastructure.config;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenCompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenCustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenCompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenCustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenCustomerRepository tokenCustomerRepository;
    private final TokenCompanyRepository tokenCompanyRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);

        TokenCustomerEntity tokenCustomer = tokenCustomerRepository.findTokenByToken(jwt).orElse(null);
        TokenCompanyEntity tokenCompany = tokenCompanyRepository.findTokenByToken(jwt).orElse(null);

        if (tokenCustomer != null) {
            tokenCustomer.setExpired(true);
            tokenCustomer.setRevoked(true);
            tokenCustomerRepository.save(tokenCustomer);
        } else if (tokenCompany != null) {
            tokenCompany.setExpired(true);
            tokenCompany.setRevoked(true);
            tokenCompanyRepository.save(tokenCompany);
        }
    }
}
