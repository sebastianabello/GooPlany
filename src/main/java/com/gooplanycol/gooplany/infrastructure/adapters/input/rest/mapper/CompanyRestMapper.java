package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequestEdit;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyRestMapper {

    Company toCompany(CompanyRequest companyResponse);

    Company toCompany(CompanyRequestEdit companyResponse);

    Company toAuthenticationCompany(AuthenticationRequest authenticationRequestDTO);

    CompanyResponse toCompanyResponse(Company company);

    AuthenticationResponse toAuthentication(Company customerRequest);

    List<CompanyResponse> toCompanyResponseList(List<Company> all);

    HistoryResponse toHistoryResponse(History history);

    List<AddressResponse> toAddressResponseList(List<Address> address);

    Address toAddress(AddressResponse addressResponseDTO);

}
