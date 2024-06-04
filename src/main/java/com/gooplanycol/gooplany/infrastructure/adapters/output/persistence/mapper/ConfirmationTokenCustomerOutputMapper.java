package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCustomer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConfirmationTokenCustomerOutputMapper {

    ConfirmationTokenCustomerEntity toConfirmationTokenCustomerEntity(ConfirmationTokenCustomer token);

    ConfirmationTokenCustomer toConfirmationTokenCustomer(ConfirmationTokenCustomerEntity confirmationTokenCustomerEntity);
}
