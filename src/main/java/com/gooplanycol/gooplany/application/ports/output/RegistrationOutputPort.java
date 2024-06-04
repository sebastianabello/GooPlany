package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;

public interface RegistrationOutputPort {
    Customer saveCustomer(Customer customer) throws IllegalAccessException;

    Company saveCompany(Company company) throws IllegalAccessException;

    String confirmToken(String token);
}
