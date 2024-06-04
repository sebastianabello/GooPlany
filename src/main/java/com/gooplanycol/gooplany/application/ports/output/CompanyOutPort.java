package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.*;

import java.util.List;

public interface CompanyOutPort {

    Company authenticate(Company authenticationCompany);

    Company getCompanyByToken(String token);

    boolean removeCompany(Long id);

    Company editData(Company companyEdit, Long id);

    Company findById(Long id);

    List<Company> findAll(Integer offset, Integer pageSize);

    History findHistory(Long id);

    List<Address> findAddress(Long id, Integer offset, Integer pageSize);

    Company findByEmail(String email);

    Company changePwd(String pwd, Long id);

    List<Address> addAddress(Address address, Long id);

    boolean removeAddress(Long addressId, Long companyId);

}
