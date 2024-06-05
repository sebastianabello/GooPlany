package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.*;
import com.gooplanycol.gooplany.domain.model.response.*;

import java.util.List;

public interface CustomerInputPort {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationCustomer);

    CustomerResponse getCustomerByToken(String token);

    boolean removeCustomer(Long id);

    CustomerResponse editData(CustomerRequestEdit customerEdit, Long id);

    CustomerResponse findById(Long id);

    List<CustomerResponse> findAll(Integer offset, Integer pageSize);

    HistoryResponse findHistory(Long id);

    List<AddressResponse> findAddress(Long id, Integer offset, Integer pageSize);

    List<CreditCardResponse> findCards(Long id, Integer offset, Integer pageSize);

    CustomerResponse findByEmail(String email);

    CustomerResponse changePwd(String pwd, Long id);

    List<AddressResponse> addAddress(AddressResponse addressResponse, Long id);

    boolean removeAddress(Long addressId, Long customerId);

    List<CreditCardResponse> addCreditCard(CreditCardResponse creditCard, Long id);

    boolean removeCreditCard(Long creditCardId, Long customerId);
}