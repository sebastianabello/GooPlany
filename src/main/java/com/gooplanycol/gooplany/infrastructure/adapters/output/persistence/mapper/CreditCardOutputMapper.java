package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditCardOutputMapper {

        CreditCardResponse toCreditCardResponse(CreditCard creditCard);
}
