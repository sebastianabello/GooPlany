package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventPostRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventPostRestMapper {

    EventPostResponse toEventPostResponse(EventPost save);

    EventPost toEventPostRequest(EventPostRequest productSoldRequestDTO);

    List<EventPostResponse> toEventPostResponseList(List<EventPost> all);
}
