package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPost;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventPostOutputMapper {

    EventPostResponse toEventPostResponse(EventPost eventPost);

}
