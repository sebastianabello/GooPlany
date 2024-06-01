package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventPostOutputMapper {

    EventPostEntity toEventPostEntity(EventPost eventPost);

    EventPost toEventPost(EventPostEntity eventPostEntity);

    List<EventPostEntity> toEventPostEntityList(List<EventPost> eventPostList);

}
