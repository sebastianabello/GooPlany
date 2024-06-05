package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerOutputMapper {

    CustomerResponse toCustomerResponse(Customer customer);
}
