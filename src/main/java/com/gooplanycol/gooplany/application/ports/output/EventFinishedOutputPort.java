package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;

import java.util.List;

public interface EventFinishedOutputPort {
    EventFinishedResponse save(EventFinishedRequest eventFinished);

    EventFinishedResponse edit(EventFinishedRequest eventFinished, Long id);

    EventFinishedResponse findById(Long id);

    List<EventFinishedResponse> findAll(Integer offset, Integer pageSize);

    EventFinishedResponse addEventParticipant(EventParticipantRequest eventParticipant, Long id);

    EventFinishedResponse removeEventParticipant(Long eventParticipantId, Long eventFinishedId);

    List<EventParticipantResponse> findEventParticipants(Long id, Integer offset, Integer pageSize);

    EventPostResponse findEventPost(Long id);
}
