package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventRegistration;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventRegistrationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventRegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventRegistrationRestMapper {
    EventRegistration toEventRegistration(EventRegistrationRequest request);

    EventRegistrationResponse toEventRegistrationResponse(EventRegistration eventRegistration);

    List<EventRegistrationResponse> toEventRegistrationResponseList(List<EventRegistration> eventRegistrations);
}
