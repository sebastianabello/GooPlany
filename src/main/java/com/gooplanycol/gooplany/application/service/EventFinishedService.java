package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventFinishedInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventFinishedOutputPort;
import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.domain.model.EventPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFinishedService implements EventFinishedInputPort {
    private final EventFinishedOutputPort eventFinishedOutputPort;

    @Override
    public EventFinished save(EventFinished eventFinished) {
        return eventFinishedOutputPort.save(eventFinished);
    }

    @Override
    public EventFinished edit(EventFinished eventFinished, Long id) {
        return eventFinishedOutputPort.edit(eventFinished, id);
    }

    @Override
    public EventFinished findById(Long id) {
        return eventFinishedOutputPort.findById(id);
    }

    @Override
    public List<EventFinished> findAll(Integer offset, Integer pageSize) {
        return eventFinishedOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventFinished addEventParticipant(EventParticipant eventParticipant, Long id) {
        return eventFinishedOutputPort.addEventParticipant(eventParticipant, id);
    }

    @Override
    public EventFinished removeEventParticipant(Long eventParticipantId, Long eventFinishedId) {
        return eventFinishedOutputPort.removeEventParticipant(eventParticipantId, eventFinishedId);
    }

    @Override
    public List<EventParticipant> findEventParticipants(Long id, Integer offset, Integer pageSize) {
        return eventFinishedOutputPort.findEventParticipants(id, offset, pageSize);
    }

    @Override
    public EventPost findEventPost(Long id) {
        return eventFinishedOutputPort.findEventPost(id);
    }
}
