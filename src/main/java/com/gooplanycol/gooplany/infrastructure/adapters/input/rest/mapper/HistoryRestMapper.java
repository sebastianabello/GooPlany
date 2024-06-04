package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.History;

import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.HistoryRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.HistoryResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HistoryRestMapper {

    HistoryResponse toHistoryResponse(History history);

    History toHistoryRequest(HistoryRequest historyRequestDTO);

    List<HistoryResponse> toHistoryResponseList(List<History> all);

    List<EventFinishedResponse> toEventFinishedResponseList(List<EventFinished> eventFinished);

    EventFinished toEventFinishedRequest(EventFinishedResponse eventFinished);
}