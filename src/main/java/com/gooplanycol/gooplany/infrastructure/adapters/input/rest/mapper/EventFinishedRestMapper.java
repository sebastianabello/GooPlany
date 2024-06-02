package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventFinishedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventFinishedRestMapper {

    EventFinished toEventFinished(EventFinishedRequest eventFinished);

    EventFinishedResponse toEventFinishedRequest(EventFinished eventFinished);
}
