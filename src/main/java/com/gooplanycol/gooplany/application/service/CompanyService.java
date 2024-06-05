package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.application.ports.output.CompanyOutputPort;
import com.gooplanycol.gooplany.domain.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.domain.model.request.CompanyRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.domain.model.response.CompanyResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyInputPort {

    private final CompanyOutputPort companyOutPort;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationCompany) {
        return companyOutPort.authenticate(authenticationCompany);
    }

    @Override
    public CompanyResponse getCompanyByToken(String token) {
        return companyOutPort.getCompanyByToken(token);
    }

    @Override
    public boolean removeCompany(Long id) {
        return companyOutPort.removeCompany(id);
    }

    @Override
    public CompanyResponse editData(CompanyRequestEdit companyEdit, Long id) {
        return companyOutPort.editData(companyEdit, id);
    }

    @Override
    public CompanyResponse findById(Long id) {
        return companyOutPort.findById(id);
    }

    @Override
    public List<CompanyResponse> findAll(Integer offset, Integer pageSize) {
        return companyOutPort.findAll(offset, pageSize);
    }

    @Override
    public HistoryResponse findHistory(Long id) {
        return companyOutPort.findHistory(id);
    }

    @Override
    public List<AddressResponse> findAddress(Long id, Integer offset, Integer pageSize) {
        return companyOutPort.findAddress(id, offset, pageSize);
    }

    @Override
    public CompanyResponse findByEmail(String email) {
        return companyOutPort.findByEmail(email);
    }

    @Override
    public CompanyResponse changePwd(String pwd, Long id) {
        return companyOutPort.changePwd(pwd, id);
    }

    @Override
    public List<AddressResponse> addAddress(AddressResponse address, Long id) {
        return companyOutPort.addAddress(address, id);
    }

    @Override
    public boolean removeAddress(Long addressId, Long customerId) {
        return companyOutPort.removeAddress(addressId, customerId);
    }
}
