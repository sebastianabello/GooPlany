package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyOutputMapper {
    CompanyEntity toCompanyEntity(Company company);
    Company toCompany(CompanyEntity entity);
    List<Company> toCompanyList(List<CompanyEntity> entityList);
    Optional<Company> toCompanyOptional(Company company);
    Page<Company> toCompanyPage(Page<CompanyEntity> entityPage);
}
