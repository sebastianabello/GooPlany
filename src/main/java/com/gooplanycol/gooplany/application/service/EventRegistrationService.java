package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventRegistrationInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventRegistrationOutputPort;
import com.gooplanycol.gooplany.domain.model.EventRegistration;
import com.gooplanycol.gooplany.utils.StatusRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventRegistrationService implements EventRegistrationInputPort {

    private final EventRegistrationOutputPort eventRegistrationOutputPort;

    @Override
    public EventRegistration save(EventRegistration eventRegistration) {
        return eventRegistrationOutputPort.save(eventRegistration);
    }

    @Override
    public boolean remove(Long id) {
        return eventRegistrationOutputPort.remove(id);
    }

    @Override
    public boolean isProfileRegisteredEvent(Long profileId, Long eventId) {
        return eventRegistrationOutputPort.isProfileRegisteredEvent(profileId, eventId);
    }

    @Override
    public List<EventRegistration> findAllByEventPostId(Long eventPostId) {
        return eventRegistrationOutputPort.findAllByEventPostId(eventPostId);
    }

    @Override
    public List<EventRegistration> findByStatusRegistrationEvent(StatusRegistrationEvent statusRegistrationEvent) {
        return eventRegistrationOutputPort.findByStatusRegistrationEvent(statusRegistrationEvent);
    }
}
