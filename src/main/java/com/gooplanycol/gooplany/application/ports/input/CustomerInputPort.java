package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.*;
import com.gooplanycol.gooplany.domain.model.response.*;

import java.util.List;

public interface CustomerInputPort {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    CustomerResponse getCustomerByToken(String token);

    boolean removeCustomer(Long id);

    CustomerResponse editData(CustomerRequestEdit responseDTO, Long id);

    CustomerResponse findById(Long id);

    List<CustomerResponse> findAll(Integer offset, Integer pageSize);

    HistoryResponse findHistory(Long id);

    List<AddressResponse> findAddress(Long id, Integer offset, Integer pageSize);

    List<CreditCardResponse> findCards(Long id, Integer offset, Integer pageSize);

    CustomerResponse findByEmail(String email);

    CustomerResponse changePwd(String pwd, Long id);

    List<AddressResponse> addAddress(AddressResponse addressRequestDTO, Long id);

    boolean removeAddress(Long idAddress, Long idCustomer);

    List<CreditCardResponse> addCreditCard(CreditCardResponse creditCardResponse, Long id);

    boolean removeCreditCard(Long idCreditCard, Long idCustomer);
}