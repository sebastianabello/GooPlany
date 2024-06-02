package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventParticipantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventParticipantRestMapper {

    EventParticipant toEventParticipant(EventParticipantRequest eventParticipantRequest);

    EventParticipantResponse toEventParticipantResponse(EventParticipant eventParticipant);
}
