package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.CustomerRequest;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;

public interface RegistrationInputPort {

    AuthenticationResponse saveCustomer(CustomerRequest customerRequest) throws IllegalAccessException;

    String confirmToken(String token);

}
