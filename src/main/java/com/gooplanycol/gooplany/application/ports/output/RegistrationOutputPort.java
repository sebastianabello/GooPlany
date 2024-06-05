package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.request.CompanyRequest;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequest;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;

public interface RegistrationOutputPort {
    AuthenticationResponse saveCustomer(CustomerRequest customerRequest) throws IllegalAccessException;

    AuthenticationResponse saveCompany(CompanyRequest companyRequest) throws IllegalAccessException;

    String confirmToken(String token);
}
