package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.application.ports.output.RegistrationOutputPort;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequest;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegistrationInputPort {

    private final RegistrationOutputPort registrationOutputPort;

    @Override
    public AuthenticationResponse saveCustomer(CustomerRequest customerRequest) throws IllegalAccessException {
        return registrationOutputPort.save(customerRequest);
    }

    @Override
    public String confirmToken(String token) {
        return registrationOutputPort.confirmToken(token);
    }
}
