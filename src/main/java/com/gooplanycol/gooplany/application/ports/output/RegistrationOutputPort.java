package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;

public interface RegistrationOutputPort {
    Customer save(Customer customer) throws IllegalAccessException;

    Company save(Company company) throws IllegalAccessException;

    String confirmToken(String token);
}
