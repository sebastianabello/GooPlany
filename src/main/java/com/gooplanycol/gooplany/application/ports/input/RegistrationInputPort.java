package com.gooplanycol.gooplany.application.ports.input;


import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

public interface RegistrationInputPort {

    AuthenticationResponse save(Profile profile) throws IllegalAccessException;

    AuthenticationResponse save(Company company) throws IllegalAccessException;

    String confirmToken(String token);
}
