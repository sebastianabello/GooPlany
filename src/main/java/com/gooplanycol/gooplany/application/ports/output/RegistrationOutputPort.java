package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.request.CustomerRequest;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;

public interface RegistrationOutputPort {
    AuthenticationResponse save(CustomerRequest customerRequest) throws IllegalAccessException;

    String confirmToken(String token);
}
