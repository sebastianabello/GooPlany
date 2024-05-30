package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventPostOutputMapper {

    EventPostEntity toEventPostEntity(EventPost eventPost);

    EventPost toEventPost(EventPostEntity eventPostEntity);

    List<EventPost> toEventPostList(List<EventPostEntity> entityList);

    Page<EventPost> toEventPostPage(Page<EventPostEntity> entityPage);
}
