package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventParticipantOutputMapper {
    EventParticipantResponse toEventParticipantResponse(EventParticipant eventParticipant);
}
