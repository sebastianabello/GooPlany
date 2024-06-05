package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.CompanyResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyOutputMapper {

    CompanyResponse toCompanyResponse(Company customer);
}
