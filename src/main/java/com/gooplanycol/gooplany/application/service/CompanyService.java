package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.application.ports.output.CompanyOutPort;
import com.gooplanycol.gooplany.application.ports.output.UserOutputPort;
import com.gooplanycol.gooplany.domain.exception.CompanyNotFoundException;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyInputPort {

    private final CompanyOutPort companyOutPort;
    private final UserOutputPort userOutputPort;
    private final AddressOutputPort addressOutputPort;


    @Override
    public Company findById(Long id) {
        return companyOutPort.findById(id)
                .orElseThrow(CompanyNotFoundException::new);
    }

    @Override
    public List<Company> findAll() {
        return companyOutPort.findAll();
    }

    @Override
    public Company save(Company company) {
        User user = company.getUser();
        user = userOutputPort.save(user);
        company.setUser(user);

        Address address = company.getAddress();
        address = addressOutputPort.save(address);
        company.setAddress(address);

        return companyOutPort.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        return companyOutPort.findById(id)
                .map(companyFound -> {
                    companyFound.setName(company.getName());
                    companyFound.setPhoneNumber(company.getPhoneNumber());
                    companyFound.setAddress(company.getAddress());
                    return companyOutPort.save(companyFound);
                })
                .orElseThrow(CompanyNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        companyOutPort.deleteById(id);
    }
}
