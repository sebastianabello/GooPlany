package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.*;

import java.util.List;

public interface CustomerInputPort {

    Customer authenticate(Customer authenticationCustomer);

    Customer getCustomerByToken(String token);

    boolean removeCustomer(Long id);

    Customer editData(Customer customerEdit, Long id);

    Customer findById(Long id);

    List<Customer> findAll(Integer offset, Integer pageSize);

    History findHistory(Long id);

    List<Address> findAddress(Long id, Integer offset, Integer pageSize);

    List<CreditCard> findCards(Long id, Integer offset, Integer pageSize);

    Customer findByEmail(String email);

    Customer changePwd(String pwd, Long id);

    List<Address> addAddress(Address addressRequestDTO, Long id);

    boolean removeAddress(Long addressId, Long customerId);

    List<CreditCard> addCreditCard(CreditCard creditCard, Long id);

    boolean removeCreditCard(Long creditCardId, Long customerId);
}