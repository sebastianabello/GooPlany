package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper;

import com.gooplanycol.gooplany.domain.model.HistoryCompany;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.HistoryRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.HistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryRestMapper {

    HistoryCompany toHistory(HistoryRequest historyResponse);

    HistoryResponse toHistoryResponse(HistoryCompany historyCompany);

    List<EventPostResponse> eventPostsResponse(List<EventPostResponse> eventPosts);
}
