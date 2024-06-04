package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerOutputMapper {

    Customer toCustomer(CustomerEntity entity);

}
