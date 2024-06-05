package com.gooplanycol.gooplany.domain.model.request;

import java.time.LocalDateTime;
import java.util.List;

public record EventFinishedRequest(
         String concept,
         EventPostRequest eventPost,
         LocalDateTime createAt,
         List<EventParticipantRequest> eventParticipants
) {
}
