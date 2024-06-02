package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record HistoryResponse(
        Long id,
        EventPostResponse eventPosts,
        List<EventParticipantResponse> eventParticipants,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateModification
) {
}
