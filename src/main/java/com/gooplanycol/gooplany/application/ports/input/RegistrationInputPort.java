package com.gooplanycol.gooplany.application.ports.input;


import com.gooplanycol.gooplany.domain.model.Authentication;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

public interface RegistrationInputPort {

    Authentication save(Customer customer) throws IllegalAccessException;

    Authentication save(Company company) throws IllegalAccessException;

    String confirmToken(String token);

}
