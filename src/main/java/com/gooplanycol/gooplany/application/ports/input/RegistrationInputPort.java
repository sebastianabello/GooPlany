package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;

public interface RegistrationInputPort {

    Customer saveCustomer(Customer company) throws IllegalAccessException;

    Company saveCompany(Company company) throws IllegalAccessException;

    String confirmToken(String token);

}
