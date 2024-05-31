package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.HistoryCompany;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryCompanyOutputMapper {

    HistoryCompanyEntity toHistoryCompanyEntity(HistoryCompany historyCompanyEntity);

    HistoryCompany toHistoryCompany(HistoryCompanyEntity historyCompanyEntity);

}
