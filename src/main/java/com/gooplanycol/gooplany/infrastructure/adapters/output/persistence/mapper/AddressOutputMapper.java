package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressOutputMapper {

    AddressResponse toAddressResponse(Address address);
}
