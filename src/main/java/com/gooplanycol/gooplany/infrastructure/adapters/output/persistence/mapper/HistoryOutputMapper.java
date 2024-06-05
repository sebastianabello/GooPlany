package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper;

import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinished;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.History;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryOutputMapper {

    HistoryResponse toHistoryResponse(History history);
    List<EventFinishedResponse> eventFinishedResponse(List<EventFinished> eventFinishedList);
}
