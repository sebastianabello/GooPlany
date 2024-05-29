package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.HistoryRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.HistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryRestMapper {

    History toHistory(HistoryRequest historyResponse);
    HistoryResponse toHistoryResponse(History history);
    List<EventPostResponse> eventPostsResponse(List<EventPostResponse> eventPosts);
}
