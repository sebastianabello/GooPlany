package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;

import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.application.ports.output.CompanyOutPort;
import com.gooplanycol.gooplany.application.ports.output.ConfirmationTokenOutputPort;
import com.gooplanycol.gooplany.application.ports.output.HistoryOutputPort;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.domain.model.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.utils.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyInputPort {

    private final CompanyOutPort companyOutPort;
    private final AddressOutputPort addressOutputPort;
    private final HistoryOutputPort historyOutputPort;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ConfirmationTokenOutputPort confirmationTokenOutputPort;
    private final PasswordEncoder passwordEncoder;

    private void saveCompanyToken(Company company, String jwtToken) {
        Token token = new Token(
                null,
                jwtToken,
                TokenType.BEARER,
                company,
                false,
                false
        );
    }

    private void revokeAllCompanyTokens(Company customer) {
        List<Token> validCustomerTokens = tokenRepository.findAllValidTokenByCustomer(customer.getId());
        if (validCustomerTokens.isEmpty())
            return;
        validCustomerTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.username();
        String pwd = authenticationRequest.password();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, pwd)
        );
        Company company = companyOutPort.findByEmail(username);
        String jwtToken = jwtService.createToken(company);
        revokeAllCompanyTokens(company);
        saveCompanyToken(company, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }


    @Override
    public boolean removeCompany(Long id) {
        return false;
    }

    @Override
    public Company editData(Company response, Long id) {
        return null;
    }

    @Override
    public Company findById(Long id) {
        return null;
    }

    @Override
    public List<Company> findAll(Integer offset, Integer pageSize) {
        return List.of();
    }

    @Override
    public History findHistory(Long id) {
        return null;
    }

    @Override
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        return List.of();
    }

    @Override
    public Company findByEmail(String email) {
        return null;
    }

    @Override
    public Company changePwd(String pwd, Long id) {
        return null;
    }

    @Override
    public List<Address> addAddress(Address address, Long id) {
        return List.of();
    }

    @Override
    public boolean removeAddress(Long addressId, Long customerId) {
        return false;
    }
}
