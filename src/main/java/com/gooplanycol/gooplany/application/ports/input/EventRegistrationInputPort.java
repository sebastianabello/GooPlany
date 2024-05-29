package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventRegistration;

import java.util.List;

public interface EventRegistrationInputPort {

    EventRegistration save(EventRegistration eventRegistration);

    boolean remove(Long id);

    boolean isProfileRegisteredEvent(Long profileId, Long eventId);

    List<EventRegistration> findAllByEventPostId(Long eventPostId);

    List<EventRegistration> findByStatusRegistrationEvent(String statusRegistrationEvent);


}
