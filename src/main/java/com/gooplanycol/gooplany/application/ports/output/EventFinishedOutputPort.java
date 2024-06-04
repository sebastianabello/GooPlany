package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.domain.model.EventPost;

import java.util.List;

public interface EventFinishedOutputPort {
    EventFinished save(EventFinished eventFinished);

    EventFinished edit(EventFinished eventFinished, Long id);

    EventFinished findById(Long id);

    List<EventFinished> findAll(Integer offset, Integer pageSize);

    EventFinished addEventParticipant(EventParticipant eventParticipant, Long id);

    EventFinished removeEventParticipant(Long eventParticipantId, Long eventFinishedId);

    List<EventParticipant> findEventParticipants(Long id, Integer offset, Integer pageSize);

    EventPost findEventPost(Long id);
}
