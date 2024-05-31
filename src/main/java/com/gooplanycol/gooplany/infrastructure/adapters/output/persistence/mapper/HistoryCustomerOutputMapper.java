package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.HistoryCustomer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryCustomerOutputMapper {

    HistoryCustomerEntity toHistoryCustomerEntity(HistoryCustomer historyCustomer);

    HistoryCustomer toHistoryCustomer(HistoryCustomerEntity historyCustomerEntity);

}
