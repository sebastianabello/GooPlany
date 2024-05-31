package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventRegistrationOutputMapper {
    EventRegistrationEntity toEventRegistrationEntity(EventRegistration eventRegistration);
    EventRegistration toEventRegistration(EventRegistrationEntity eventRegistrationEntity);
    List<EventRegistration> toEventRegistrationList(List<EventRegistrationEntity> eventRegistrationEntities);
}
