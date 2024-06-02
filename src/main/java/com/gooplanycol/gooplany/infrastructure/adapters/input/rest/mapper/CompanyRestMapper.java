package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.Authentication;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyRestMapper {

    @Mappings({
            @org.mapstruct.Mapping(target = "name", source = "name"),
            @org.mapstruct.Mapping(target = "email", source = "email"),
            @org.mapstruct.Mapping(target = "cellphone", source = "cellphone"),
            @org.mapstruct.Mapping(target = "username", source = "username"),
            @org.mapstruct.Mapping(target = "pwd", source = "pwd"),
            @org.mapstruct.Mapping(target = "address", source = "address"),
            @org.mapstruct.Mapping(target = "createAt", source = "createdAt"),
    })
    Company toCompany(CompanyRequest customerRequest);

    @Mappings({
            @org.mapstruct.Mapping(target = "username", source = "username"),
            @org.mapstruct.Mapping(target = "pwd", source = "password"),
    })
    Company toCompany(AuthenticationRequest authenticationRequest);

    @Mappings({
            @org.mapstruct.Mapping(target = "token", source = "token"),
    })
    AuthenticationResponse toAuthenticationResponse(Authentication authentication);


}
