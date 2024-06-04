package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCompany;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConfirmationTokenCompanyOutputMapper {

    ConfirmationTokenCompanyEntity toConfirmationTokenCompanyEntity(ConfirmationTokenCompany token);

    ConfirmationTokenCompany toConfirmationTokenCompany(ConfirmationTokenCompanyEntity confirmationTokenCompanyEntity);
}
