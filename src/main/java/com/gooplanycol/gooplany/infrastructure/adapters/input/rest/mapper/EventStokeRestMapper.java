package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventStoke;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventStokeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventStokeRestMapper {

    EventStoke toEventStoke(EventStokeRequest eventStoke);

    EventStokeResponse toEventStokeResponse(EventStoke eventStoke);
}
