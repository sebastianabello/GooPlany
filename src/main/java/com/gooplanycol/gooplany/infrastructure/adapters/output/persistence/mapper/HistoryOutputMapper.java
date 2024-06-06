package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.History;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HistoryOutputMapper {

    HistoryResponse toHistoryResponse(History history);
    default Long map(CreditCard creditCard) {
        return creditCard.getId();
    }

    default Long map(Customer customer) {
        return customer.getId();
    }


}
