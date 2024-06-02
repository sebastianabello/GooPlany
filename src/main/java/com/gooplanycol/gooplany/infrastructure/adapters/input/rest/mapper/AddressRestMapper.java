package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AddressRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressRestMapper {

    @Mappings({
            @org.mapstruct.Mapping(target = "street", source = "street"),
            @org.mapstruct.Mapping(target = "country", source = "country"),
            @org.mapstruct.Mapping(target = "postalCode", source = "postalCode")
    })
    Address toAddress(AddressRequest addressRequest);

    @Mappings({
            @org.mapstruct.Mapping(target = "street", source = "street"),
            @org.mapstruct.Mapping(target = "country", source = "country"),
            @org.mapstruct.Mapping(target = "postalCode", source = "postalCode")
    })
    AddressResponse toAddressResponse(Address address);

    @Mappings({
            @org.mapstruct.Mapping(target = "street", source = "street"),
            @org.mapstruct.Mapping(target = "country", source = "country"),
            @org.mapstruct.Mapping(target = "postalCode", source = "postalCode")
    })
    List<AddressResponse> toAddressResponseList(List<Address> addressList);

}
