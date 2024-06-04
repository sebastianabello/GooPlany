package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;

import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyOutputMapper {

    Company toCompany(CompanyEntity entity);

}
