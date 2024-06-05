package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.domain.model.request.CompanyRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.domain.model.response.CompanyResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;

import java.util.List;

public interface CompanyOutputPort {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationCompany);

    CompanyResponse getCompanyByToken(String token);

    boolean removeCompany(Long id);

    CompanyResponse editData(CompanyRequestEdit companyEdit, Long id);

    CompanyResponse findById(Long id);

    List<CompanyResponse> findAll(Integer offset, Integer pageSize);

    HistoryResponse findHistory(Long id);

    List<AddressResponse> findAddress(Long id, Integer offset, Integer pageSize);

    CompanyResponse findByEmail(String email);

    CompanyResponse changePwd(String pwd, Long id);

    List<AddressResponse> addAddress(AddressResponse address, Long id);

    boolean removeAddress(Long addressId, Long companyId);

}
