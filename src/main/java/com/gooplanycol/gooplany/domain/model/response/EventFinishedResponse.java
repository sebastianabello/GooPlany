package com.gooplanycol.gooplany.domain.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record EventFinishedResponse(
        Long id,
        EventPostResponse eventPost,
        LocalDateTime createAt,
        List<EventParticipantResponse> eventParticipants
) {
}