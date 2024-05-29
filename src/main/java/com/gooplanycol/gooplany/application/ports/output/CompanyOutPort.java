package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

public interface CompanyOutPort {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO);

    Company save(Company address);

    Company getCustomerByToken(String token);

    boolean removeCompany(Long id);

    // Company editData(Company response, Long id);

    Optional<Company> findById(Long id);

    List<Company> findAll(Integer offset, Integer pageSize);

    History findHistory(Long id);

    List<Address> findAddress(Long id, Integer offset, Integer pageSize);

    Company findByEmail(String email);

    Company changePwd(String pwd, Long id);

    List<Address> addAddress(Address address, Long id);

    boolean removeAddress(Long addressId, Long companyId);
}
