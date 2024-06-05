package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventFinishedInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventFinishedOutputPort;
import com.gooplanycol.gooplany.domain.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFinishedService implements EventFinishedInputPort {

    private final EventFinishedOutputPort eventFinishedOutputPort;

    @Override
    public EventFinishedResponse save(EventFinishedRequest eventFinished) {
        return eventFinishedOutputPort.save(eventFinished);
    }

    @Override
    public EventFinishedResponse edit(EventFinishedRequest eventFinished, Long id) {
        return eventFinishedOutputPort.edit(eventFinished, id);
    }

    @Override
    public EventFinishedResponse findById(Long id) {
        return eventFinishedOutputPort.findById(id);
    }

    @Override
    public List<EventFinishedResponse> findAll(Integer offset, Integer pageSize) {
        return eventFinishedOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventFinishedResponse addEventParticipant(EventParticipantRequest eventParticipant, Long id) {
        return eventFinishedOutputPort.addEventParticipant(eventParticipant, id);
    }

    @Override
    public EventFinishedResponse removeEventParticipant(Long eventParticipantId, Long eventFinishedId) {
        return eventFinishedOutputPort.removeEventParticipant(eventParticipantId, eventFinishedId);
    }

    @Override
    public List<EventParticipantResponse> findEventParticipants(Long id, Integer offset, Integer pageSize) {
        return eventFinishedOutputPort.findEventParticipants(id, offset, pageSize);
    }

    @Override
    public EventPostResponse findEventPost(Long id) {
        return eventFinishedOutputPort.findEventPost(id);
    }
}
