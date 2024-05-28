package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyRestMapper {
    Company toCompany(CompanyRequest request);
    CompanyResponse toCompanyResponse(Company company);
    List<CompanyResponse> toCompanyResponseList(List<Company> companies);
}
