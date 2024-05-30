package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.application.ports.output.RegistrationCompanyOutputPort;
import com.gooplanycol.gooplany.application.ports.output.RegistrationProfileOutputPort;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegistrationInputPort {

    private final RegistrationProfileOutputPort registrationProfileOutputPort;
    private final RegistrationCompanyOutputPort registrationCompanyOutputPort;

    @Override
    public AuthenticationResponse save(Profile profile) throws IllegalAccessException {
        return registrationProfileOutputPort.save(profile);
    }

    @Override
    public AuthenticationResponse save(Company company) throws IllegalAccessException {
        return registrationCompanyOutputPort.save(company);
    }

}
