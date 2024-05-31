package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.application.ports.output.RegistrationOutputPort;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegistrationInputPort {

    private final RegistrationOutputPort registrationOutputPort;

    @Override
    public Customer save(Customer customer) throws IllegalAccessException {
        return registrationOutputPort.save(customer);
    }

    @Override
    public Company save(Company company) throws IllegalAccessException {
        return registrationOutputPort.save(company);
    }

    @Override
    public String confirmToken(String token) {
        return registrationOutputPort.confirmToken(token);
    }
}
