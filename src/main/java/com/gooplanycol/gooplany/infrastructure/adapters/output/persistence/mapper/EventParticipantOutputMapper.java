package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventParticipantOutputMapper {

    EventParticipantEntity toEventParticipantEntity(EventParticipant eventParticipantEntity);

    EventParticipant toEventParticipant(EventParticipantEntity eventParticipantEntity);

}
