package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinished;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventFinishedOutputMapper {

    EventFinishedResponse toEventFinishedResponse(EventFinished eventFinished);

    List<EventParticipantResponse> toEventParticipantResponse(List<EventParticipantResponse> eventParticipantResponseList);

}
