package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventFinishedRestMapper {

    EventFinished toEventFinishedRequest(EventFinishedRequest eventFinished);

    EventFinishedResponse toEventFinishedResponse(EventFinished save);

    List<EventFinishedResponse> toEventFinishedResponseList(List<EventFinished> all);

    EventParticipant toEventParticipantRequest(EventParticipantRequest eventParticipantRequest);

    List<EventParticipantResponse> toEventParticipantResponseList(List<EventParticipant> eventParticipants);

    EventPostResponse toEventPostResponse(EventPost eventPost);
}
