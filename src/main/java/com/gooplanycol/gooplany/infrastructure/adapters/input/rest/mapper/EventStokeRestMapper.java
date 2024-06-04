package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventStoke;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventStokeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventStokeRestMapper {

    EventStokeResponse toEventStokeResponse(EventStoke eventStoke);

    EventStoke toEventStokeRequest(EventStokeRequest eventStokeRequest);

    List<EventStokeResponse> toEventStokeResponseList(List<EventStoke> all);
}
