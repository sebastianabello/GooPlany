package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.application.ports.output.CustomerOutputPort;
import com.gooplanycol.gooplany.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInputPort {

    private final CustomerOutputPort customerOutputPort;

    @Override
    public Customer authenticate(Customer authenticationCustomer) {
        return customerOutputPort.authenticate(authenticationCustomer);
    }

    @Override
    public Customer getCustomerByToken(String token) {
        return customerOutputPort.getCustomerByToken(token);
    }

    @Override
    public boolean removeCustomer(Long id) {
        return customerOutputPort.removeCustomer(id);
    }

    @Override
    public Customer editData(Customer response, Long id) {
        return customerOutputPort.editData(response, id);
    }

    @Override
    public Customer findById(Long id) {
        return customerOutputPort.findById(id);
    }

    @Override
    public List<Customer> findAll(Integer offset, Integer pageSize) {
        return customerOutputPort.findAll(offset, pageSize);
    }

    @Override
    public History findHistory(Long id) {
        return customerOutputPort.findHistory(id);
    }

    @Override
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        return customerOutputPort.findAddress(id, offset, pageSize);
    }

    @Override
    public List<CreditCard> findCards(Long id, Integer offset, Integer pageSize) {
        return customerOutputPort.findCards(id, offset, pageSize);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerOutputPort.findByEmail(email);
    }

    @Override
    public Customer changePwd(String pwd, Long id) {
        return customerOutputPort.changePwd(pwd, id);
    }

    @Override
    public List<Address> addAddress(Address addressRequestDTO, Long id) {
        return customerOutputPort.addAddress(addressRequestDTO, id);
    }

    @Override
    public boolean removeAddress(Long addressId, Long customerId) {
        return customerOutputPort.removeAddress(addressId, customerId);
    }

    @Override
    public List<CreditCard> addCreditCard(CreditCard creditCard, Long id) {
        return customerOutputPort.addCreditCard(creditCard, id);
    }

    @Override
    public boolean removeCreditCard(Long creditCardId, Long customerId) {
        return customerOutputPort.removeCreditCard(creditCardId, customerId);
    }

}
