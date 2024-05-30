package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.ConfirmationToken;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfirmationTokenOutputMapper {

    ConfirmationTokenEntity toConfirmationTokenEntity(ConfirmationToken confirmationTokenEntity);
    ConfirmationToken toConfirmationToken(ConfirmationTokenEntity confirmationTokenEntity);
}
