package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.HistoryRequest;

import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.HistoryResponse;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HistoryRestMapper {

    History toHistory(HistoryRequest historyRequest);

    HistoryResponse toHistoryResponse(History history);
}