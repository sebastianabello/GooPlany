package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventFinishedOutputMapper {

    EventFinishedEntity toEventFinishedEntity(EventFinished eventFinishedEntity);

    EventFinished toEventFinished(EventFinishedEntity eventFinishedEntity);
}
