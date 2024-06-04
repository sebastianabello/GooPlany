package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CustomerRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CustomerRequestEdit;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerRestMapper {

    Customer toCustomer(CustomerRequest customerResponse);

    Customer toCustomer(CustomerRequestEdit customerResponse);

    Customer toAthenticationCustomer(AuthenticationRequest authenticationRequest);

    CustomerResponse toCustomerResponse(Customer customer);

    AuthenticationResponse toAuthentication(Customer customerRequest);

    List<CustomerResponse> toCustomerResponseList(List<Customer> all);

    HistoryResponse toHistoryResponse(History history);

    List<AddressResponse> toAddressResponseList(List<Address> address);

    List<CreditCardResponse> toCreditCardResponseList(List<CreditCard> cards);

    Address toAddress(AddressResponse addressResponseDTO);

    CreditCard toCreditCard(CreditCardResponse creditCardResponseDTO);

    Customer toAuthenticationCustomer(AuthenticationRequest authenticationRequest);
}
