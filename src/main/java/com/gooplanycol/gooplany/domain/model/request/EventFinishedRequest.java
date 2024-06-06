package com.gooplanycol.gooplany.domain.model.request;

import java.time.LocalDateTime;
import java.util.List;

public record EventFinishedRequest(
         EventPostRequest eventPost,
         LocalDateTime createAt,
         List<EventParticipantRequest> eventParticipants
) {
}
