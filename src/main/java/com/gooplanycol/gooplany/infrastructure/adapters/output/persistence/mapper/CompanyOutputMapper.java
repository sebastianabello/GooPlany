package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyOutputMapper {
    CompanyEntity toCompanyEntity(Company company);
    Company toCompany(CompanyEntity entity);
    List<Company> toCompanyList(List<CompanyEntity> entityList);
}
