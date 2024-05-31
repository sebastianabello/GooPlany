package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.application.ports.output.RegistrationOutputPort;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegistrationInputPort {

    private final RegistrationProfileOutputPort registrationProfileOutputPort;
    private final RegistrationOutputPort registrationOutputPort;

    @Override
    public AuthenticationResponse save(Customer customer) throws IllegalAccessException {
        return registrationProfileOutputPort.save(customer);
    }

    @Override
    public AuthenticationResponse save(Company company) throws IllegalAccessException {
        return registrationOutputPort.save(company);
    }

}
