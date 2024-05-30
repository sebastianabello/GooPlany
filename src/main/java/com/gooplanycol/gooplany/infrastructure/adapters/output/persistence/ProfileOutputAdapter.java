package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.ProfileOutputPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.domain.model.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ProfileOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.TokenOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventRegistrationRepository;
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
public class ProfileOutputAdapter implements ProfileOutputPort {
    private final ProfileRepository profileRepository;
    private final ProfileOutputMapper profileOutputMapper;

    private final TokenRepository tokenRepository;
    private final TokenOutputMapper tokenOutputMapper;
    private final AuthenticationManager authenticationManager;
    private final EventPostRepository eventPostRepository;

    private final JwtService jwtService;

    // private final ConfirmationTokenOutputAdapter confirmationTokenOutputAdapter;

    private final PasswordEncoder passwordEncoder;

    private void saveProfileToken(Profile profile, String jwtToken) {
        Token token = new Token(
                null,
                jwtToken,
                TokenType.BEARER,
                profile,
                false,
                false
        );
        tokenOutputMapper.toToken(tokenRepository.save(tokenOutputMapper.toTokenEntity(token)));
    }

    private void revokeAllProfileTokens(Profile profile) {
        List<Token> validProfileTokens = tokenOutputMapper.toTokenList(tokenRepository.findAllValidTokenByProfile(profile.getId()));
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
        Profile profile = profileOutputMapper.toProfile(profileRepository.findProfileByUsername(username).orElse(null));
        String jwtToken = jwtService.generateToken(profileOutputMapper.toProfileEntity(profile));
        revokeAllProfileTokens(profile);
        saveProfileToken(profile, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public Profile getProfileByToken(String token) {
        Profile profile = profileOutputMapper.toProfile(tokenRepository.getProfileByToken(token));
        if (profile != null) {
            return profile;
        } else {
            throw new CompanyException("The profile fetched by token doesn't exist");
        }
    }

    @Override
    public Profile save(Profile profile) {
        return profileOutputMapper.toProfile(profileRepository.save(profileOutputMapper.toProfileEntity(profile)));
    }

    @Override
    public boolean removeProfile(Long id) {
        Profile profile = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (profile != null) {
            if (profile.getTokens() != null) {
                profile.getTokens().forEach(token -> token.setCompany(null));
            }
            if (profile.getConfirmationTokens() != null) {
                profile.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCompany(null));
            }
            profile.setHistory(null);
            profileRepository.delete(profileOutputMapper.toProfileEntity(profile));
            return true;
        } else {
            throw new CompanyException("The profile fetched to delete doesn't exist");
        }
    }

    @Override
    public Optional<Profile> findById(Long id) {
        Profile profile = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (profile != null) {
            return profileOutputMapper.toProfileOptional(profile);
        } else {
            throw new CompanyException("The profile fetched by id doesn't exist");
        }
    }

    @Override
    public List<Profile> findAll(Integer offset, Integer pageSize) {
        Page<Profile> list = profileOutputMapper.toProfilePage(profileRepository.findAll(PageRequest.of(offset, pageSize)));
        if (list != null && !list.isEmpty()) {
            return new ArrayList<>(list.getContent());
        } else {
            throw new CompanyException("The list of companies is null");
        }
    }

    @Override
    public Profile findByEmail(String email) {
        Profile profile = profileOutputMapper.toProfile(profileRepository.findProfileByEmail(email).orElse(null));
        if (profile != null) {
            return profile;
        } else {
            throw new CompanyException("The company fetched by email doesn't exist");
        }
    }

    @Override
    public Profile changePwd(String pwd, Long id) {
        Profile profile = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (profile != null) {
            profile.setPwd(passwordEncoder.encode(pwd));
            return profileOutputMapper.toProfile(profileRepository.save(profileOutputMapper.toProfileEntity(profile)));
        } else {
            throw new CompanyException("The company fetched to change its pwd doesn't exist");
        }
    }

}
