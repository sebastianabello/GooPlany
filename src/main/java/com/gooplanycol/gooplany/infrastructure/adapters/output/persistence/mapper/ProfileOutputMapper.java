package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileOutputMapper {

    CustomerEntity toProfileEntity(Customer customer);

    Customer toProfile(CustomerEntity customerEntity);

    List<Customer> toProfileList(List<CustomerEntity> profileEntities);

    Page<Customer> toProfilePage(Page<CustomerEntity> profileEntityPage);
}
