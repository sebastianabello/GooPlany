package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Authentication;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;

public interface RegistrationOutputPort {
    Authentication save(Customer customer) throws IllegalAccessException;

    Authentication save(Company company) throws IllegalAccessException;

    String confirmToken(String token);
}
