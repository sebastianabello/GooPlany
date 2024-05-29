package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;

import com.gooplanycol.gooplany.application.ports.output.*;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyInputPort {

    @Qualifier("companyOutputAdapter")
    private final CompanyOutPort companyOutPort;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ConfirmationTokenOutputPort confirmationTokenOutputPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return companyOutPort.authenticate(authenticationRequest);
    }

    @Override
    public boolean removeCompany(Long id) {
        return companyOutPort.removeCompany(id);
    }

    @Override
    public Company editData(Company response, Long id) {
        return companyOutPort.findById(id)
                .map(company -> {
                    company.setName(response.getName());
                    company.setCellphone(response.getCellphone());
                    company.setEmail(response.getEmail());
                    company.setUpdatedAt(LocalDate.now());
                    return companyOutPort.save(company);
                })
                .orElseThrow(() -> new IllegalArgumentException("The company to update doesn't exist or the request is null"));
    }

    @Override
    public Company findById(Long id) {
        return companyOutPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The company doesn't exist"));
    }

    @Override
    public List<Company> findAll(Integer offset, Integer pageSize) {
        return companyOutPort.findAll(offset, pageSize);
    }

    @Override
    public History findHistory(Long id) {
        return companyOutPort.findHistory(id);
    }

    @Override
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        return companyOutPort.findAddress(id, offset, pageSize);
    }

    @Override
    public Company findByEmail(String email) {
        return companyOutPort.findByEmail(email);
    }

    @Override
    public Company changePwd(String pwd, Long id) {
        return companyOutPort.findById(id)
                .map(company -> {
                    company.setPwd(passwordEncoder.encode(pwd));
                    return companyOutPort.save(company);
                })
                .orElseThrow(() -> new IllegalArgumentException("The company to update doesn't exist or the request is null"));
    }

    @Override
    public List<Address> addAddress(Address address, Long id) {
        return companyOutPort.addAddress(address, id);
    }

    @Override
    public boolean removeAddress(Long addressId, Long customerId) {
        return companyOutPort.removeAddress(addressId, customerId);
    }
}
