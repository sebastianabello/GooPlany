package com.gooplanycol.gooplany.domain.model.request;

import com.gooplanycol.gooplany.utils.StatusEventParticipant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record EventFinishedRequest(
         EventPostRequest eventPost,
         LocalDateTime createAt,
         List<EventParticipantRequest> eventParticipants
) {
    public List<EventParticipantRequest> getRegisteredParticipants() {
        if (eventParticipants != null) {
            return eventParticipants.stream()
                    .filter(participant -> participant.statusRegistration().equals(StatusEventParticipant.REGISTERED))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
