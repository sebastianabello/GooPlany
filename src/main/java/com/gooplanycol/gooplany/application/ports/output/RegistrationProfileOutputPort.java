package com.gooplanycol.gooplany.application.ports.output;


import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

public interface RegistrationProfileOutputPort {

    AuthenticationResponse save(Profile request) throws IllegalAccessException;

    String confirmToken(String token);
}
