package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressOutputMapper {
    AddressEntity toAddressEntity(Address address);
    Address toAddress(AddressEntity address);
    List<Address> toAddressList(List<AddressEntity> addresses);
}
