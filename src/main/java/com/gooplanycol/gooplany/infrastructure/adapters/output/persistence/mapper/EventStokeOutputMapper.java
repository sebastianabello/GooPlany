package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.EventStokeResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventStoke;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventStokeOutputMapper {

    EventStokeResponse toEventStokeResponse(EventStoke eventParticipant);

}
