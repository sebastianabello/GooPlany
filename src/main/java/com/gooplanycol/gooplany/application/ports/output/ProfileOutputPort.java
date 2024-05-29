package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

public interface ProfileOutputPort {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO);

    Profile getProfileByToken(String token);

    Profile save(Profile profile);

    boolean removeProfile(Long id);

    Optional<Profile> findById(Long id);

    List<Profile> findAll(Integer offset, Integer pageSize);

    // History findHistory(Long id);

    Profile findByEmail(String email);

    Profile changePwd(String pwd, Long id);

    boolean registerProfileToEvent(Long profileId, Long eventId);

    // List<String> getEmailsByEventId(Long eventId);
}
