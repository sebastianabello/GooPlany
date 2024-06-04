package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;


import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationRestMapper {
    AuthenticationResponse toAuthenticationResponse(Customer authenticationCustomer);

    AuthenticationResponse toAuthenticationResponse(Company authenticationCompany);
}
