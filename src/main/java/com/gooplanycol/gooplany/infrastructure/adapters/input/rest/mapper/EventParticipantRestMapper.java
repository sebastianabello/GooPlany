package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CustomerResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventParticipantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventParticipantRestMapper {

    EventParticipantResponse toEventParticipantResponse(EventParticipant eventParticipant);

    EventParticipant toEventParticipantRequest(EventParticipantRequest eventParticipantRequest);

    List<EventParticipantResponse> toEventParticipantResponseList(List<EventParticipant> all);

    CustomerResponse toCustomerResponse(Customer customerEventParticipant);

    CreditCardResponse toCreditCardResponse(CreditCard cardEventParticipant);
}
