package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditCardOutPutMapper {

    CreditCard toCreditCard(CreditCardEntity creditCardEntity);

}
