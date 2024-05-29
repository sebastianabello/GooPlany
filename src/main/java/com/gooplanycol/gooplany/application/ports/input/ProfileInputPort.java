package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

import java.util.List;

public interface ProfileInputPort {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO);

    Profile getProfileByToken(String token);

    boolean removeProfile(Long id);

    Profile editData(Profile response, Long id);

    Profile findById(Long id);

    List<Profile> findAll(Integer offset, Integer pageSize);

    // History findHistory(Long id);

    Profile findByEmail(String email);

    Profile changePwd(String pwd, Long id);

    boolean registerProfileToEvent(Long profileId, Long eventId);

    // List<String> getEmailsByEventId(Long eventId);

}
