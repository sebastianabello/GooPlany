package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventStoke;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventStokeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventStokeOutputMapper {

    EventStoke toEventStoke(EventStokeEntity eventParticipantEntity);

}
