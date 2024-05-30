package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.EventRegistration;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventRegistrationOutputMapper {
    EventRegistrationEntity toEventRegistrationEntity(EventRegistration eventRegistration);
    EventRegistration toEventRegistration(EventRegistrationEntity eventRegistrationEntity);
    List<EventRegistration> toEventRegistrationList(List<EventRegistrationEntity> eventRegistrationEntities);
}
