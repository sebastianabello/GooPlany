package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;

import com.gooplanycol.gooplany.application.ports.output.*;
import com.gooplanycol.gooplany.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyInputPort {

    private final CompanyOutPort companyOutPort;

    @Override
    public Authentication authenticate(String username, String password) {
        return companyOutPort.authenticate(username, password);
    }

    @Override
    public Company getCompanyByToken(String token) {
        return companyOutPort.getCompanyByToken(token);
    }

    @Override
    public boolean removeCompany(Long id) {
        return companyOutPort.removeCompany(id);
    }

    @Override
    public Company editData(Company companyEdit, Long id) {
        return companyOutPort.editData(companyEdit, id);
    }

    @Override
    public Company findById(Long id) {
        return companyOutPort.findById(id);
    }

    @Override
    public List<Company> findAll(Integer offset, Integer pageSize) {
        return companyOutPort.findAll(offset, pageSize);
    }

    @Override
    public HistoryCompany findHistory(Long id) {
        return companyOutPort.findHistory(id);
    }

    @Override
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        return companyOutPort.findAddress(id, offset, pageSize);
    }

    @Override
    public List<EventPost> findEventPost(Long id, Integer offset, Integer pageSize) {
        return companyOutPort.findEventPost(id, offset, pageSize);
    }

    @Override
    public Company findByEmail(String email) {
        return companyOutPort.findByEmail(email);
    }

    @Override
    public Company changePwd(String pwd, Long id) {
        return companyOutPort.changePwd(pwd, id);
    }

    @Override
    public List<Address> addAddress(Address address, Long id) {
        return companyOutPort.addAddress(address, id);
    }

    @Override
    public boolean removeAddress(Long addressId, Long customerId) {
        return companyOutPort.removeAddress(addressId, customerId);
    }

    @Override
    public List<EventPost> addEventPost(EventPost eventPost, Long id) {
        return companyOutPort.addEventPost(eventPost, id);
    }

    @Override
    public boolean removeEventPost(Long eventPostId, Long companyId) {
        return companyOutPort.removeEventPost(eventPostId, companyId);
    }
}
