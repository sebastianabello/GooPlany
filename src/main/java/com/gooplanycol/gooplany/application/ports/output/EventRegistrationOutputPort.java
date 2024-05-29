package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.EventRegistration;
import com.gooplanycol.gooplany.utils.StatusRegistrationEvent;

import java.util.List;
import java.util.Optional;


public interface EventRegistrationOutputPort {

    Optional<EventRegistration> findById(Long id);

    List<EventRegistration> findAll();

    Optional<EventRegistration> findByProfileAndEventPostId(Long profileId, Long eventPostId);

    EventRegistration save(EventRegistration eventRegistration);

    boolean remove(Long id);

    boolean isProfileRegisteredEvent(Long profileId, Long eventId);

    List<EventRegistration> findAllByEventPostId(Long eventPostId);

    List<EventRegistration> findByStatusRegistrationEvent(StatusRegistrationEvent statusRegistrationEvent);

}
