package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.EventRegistration;

import java.util.List;
import java.util.Optional;


public interface EventRegistrationOutputPort {

    EventRegistration save(EventRegistration eventRegistration);

    Optional<EventRegistration> findById(Long id);

    List<EventRegistration> findAll();

    void deleteById(Long id);

    List<EventRegistration> findAllByEventPostId(Long eventPostId);

    // ahora findByProfileAndEventPostId
    Optional<EventRegistration> findByProfileAndEventPostId(Long profileId, Long eventPostId);
}
