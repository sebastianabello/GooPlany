package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventPostOutputMapper {

    EventPostEntity toEventPostEntity(EventPost eventPost);

    EventPost toEventPost(EventPostEntity eventPostEntity);

    List<EventPost> toEventPostList(List<EventPostEntity> entityList);

    Page<EventPost> toEventPostPage(Page<EventPostEntity> entityPage);
}
