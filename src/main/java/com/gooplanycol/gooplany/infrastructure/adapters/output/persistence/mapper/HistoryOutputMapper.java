package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryOutputMapper {

    History toHistory(HistoryEntity historyCustomerEntity);
}