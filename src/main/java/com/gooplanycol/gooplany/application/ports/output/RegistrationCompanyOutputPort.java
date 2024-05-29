package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

public interface RegistrationCompanyOutputPort {
    AuthenticationResponse save(Company request) throws IllegalAccessException;

    String confirmToken(String token);
}
