package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record EventFinishedResponse(
        String concept,
        EventPostResponse eventPost,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt,
        List<EventParticipantResponse> eventParticipants

) {
}