package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CreditCardRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CreditCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardRestMapper {

    CreditCard toCreditCard(CreditCardRequest creditCardRequest);

    CreditCardResponse toCreditCardResponse(CreditCard creditCard);

    List<CreditCardResponse> toCreditCardResponseList(List<CreditCard> creditCardList);
}
