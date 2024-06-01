package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Authentication;
import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.HistoryCustomer;

import java.util.List;

public interface CustomerOutputPort {

    Authentication authenticate(Customer authenticationCustomer);

    Customer getCustomerByToken(String token);

    boolean removeCustomer(Long id);

    Customer editData(Customer customerEdit, Long id);

    Customer findById(Long id);

    List<Customer> findAll(Integer offset, Integer pageSize);

    HistoryCustomer findHistory(Long id);

    List<CreditCard> findCards(Long id, Integer offset, Integer pageSize);

    Customer findByEmail(String email);

    Customer changePwd(String pwd, Long id);

    List<CreditCard> addCreditCard(CreditCard creditCard, Long id);

    boolean removeCreditCard(Long creditCardId, Long customerId);
}
