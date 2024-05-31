package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CustomerOutputPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ProfileOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.TokenOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ProfileRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenRepository;
import com.gooplanycol.gooplany.utils.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerOutputAdapter implements CustomerOutputPort {
    private final ProfileRepository profileRepository;
    private final ProfileOutputMapper profileOutputMapper;

    private final TokenRepository tokenRepository;
    private final TokenOutputMapper tokenOutputMapper;
    private final AuthenticationManager authenticationManager;
    private final EventPostRepository eventPostRepository;

    private final JwtService jwtService;

    // private final ConfirmationTokenOutputAdapter confirmationTokenOutputAdapter;

    private final PasswordEncoder passwordEncoder;

    private void saveProfileToken(Customer customer, String jwtToken) {
        Token token = new Token(
                null,
                jwtToken,
                TokenType.BEARER,
                customer,
                false,
                false
        );
        tokenOutputMapper.toToken(tokenRepository.save(tokenOutputMapper.toTokenEntity(token)));
    }

    private void revokeAllProfileTokens(Customer customer) {
        List<Token> validProfileTokens = tokenOutputMapper.toTokenList(tokenRepository.findAllValidTokenByProfile(customer.getId()));
        if (validProfileTokens.isEmpty())
            return;
        validProfileTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenOutputMapper.toTokenList(tokenRepository.saveAll(tokenOutputMapper.toTokenEntityList(validProfileTokens)));
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO) {
        String username = authenticationRequestDTO.username();
        String pwd = authenticationRequestDTO.password();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, pwd
                )
        );
        Customer customer = profileOutputMapper.toProfile(profileRepository.findProfileByUsername(username).orElse(null));
        String jwtToken = jwtService.generateToken(profileOutputMapper.toProfileEntity(customer));
        revokeAllProfileTokens(customer);
        saveProfileToken(customer, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public Customer getProfileByToken(String token) {
        Customer customer = profileOutputMapper.toProfile(tokenRepository.getProfileByToken(token));
        if (customer != null) {
            return customer;
        } else {
            throw new CompanyException("The profile fetched by token doesn't exist");
        }
    }

    @Override
    public Customer save(Customer customer) {
        return profileOutputMapper.toProfile(profileRepository.save(profileOutputMapper.toProfileEntity(customer)));
    }

    @Override
    public boolean removeProfile(Long id) {
        Customer customer = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (customer != null) {
            if (customer.getTokens() != null) {
                customer.getTokens().forEach(token -> token.setCompany(null));
            }
            if (customer.getConfirmationTokens() != null) {
                customer.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCompany(null));
            }
            customer.setHistoryCompany(null);
            profileRepository.delete(profileOutputMapper.toProfileEntity(customer));
            return true;
        } else {
            throw new CompanyException("The profile fetched to delete doesn't exist");
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Customer customer = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (customer != null) {
            return Optional.of(customer);
        } else {
            throw new CompanyException("The profile fetched by id doesn't exist");
        }
    }

    @Override
    public List<Customer> findAll(Integer offset, Integer pageSize) {
        Page<Customer> list = profileOutputMapper.toProfilePage(profileRepository.findAll(PageRequest.of(offset, pageSize)));
        if (list != null && !list.isEmpty()) {
            return new ArrayList<>(list.getContent());
        } else {
            throw new CompanyException("The list of companies is null");
        }
    }

    @Override
    public Customer findByEmail(String email) {
        Customer customer = profileOutputMapper.toProfile(profileRepository.findProfileByEmail(email).orElse(null));
        if (customer != null) {
            return customer;
        } else {
            throw new CompanyException("The company fetched by email doesn't exist");
        }
    }

    @Override
    public Customer changePwd(String pwd, Long id) {
        Customer customer = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (customer != null) {
            customer.setPwd(passwordEncoder.encode(pwd));
            return profileOutputMapper.toProfile(profileRepository.save(profileOutputMapper.toProfileEntity(customer)));
        } else {
            throw new CompanyException("The company fetched to change its pwd doesn't exist");
        }
    }

}
