package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventRegistrationOutputPort;
import com.gooplanycol.gooplany.domain.model.EventRegistration;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventRegistrationOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class EventRegistrationOutputAdapter implements EventRegistrationOutputPort {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRegistrationOutputMapper mapper;

    @Override
    public EventRegistration save(EventRegistration eventRegistration) {
        return mapper.toEventRegistration(eventRegistrationRepository.save(mapper.toEventRegistrationEntity(eventRegistration)));
    }

    @Override
    public Optional<EventRegistration> findById(Long id) {
        return eventRegistrationRepository.findById(id)
                .map(mapper::toEventRegistration);
    }

    @Override
    public List<EventRegistration> findAll() {
        return mapper.toEventRegistrationList(eventRegistrationRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        eventRegistrationRepository.deleteById(id);
    }

    @Override
    public List<EventRegistration> findAllByEventPostId(Long eventPostId) {
        return mapper.toEventRegistrationList(eventRegistrationRepository.findAllByEventPostId(eventPostId));
    }

    @Override
    public Optional<EventRegistration> findByProfileAndEventPostId(Long profileId, Long eventPostId) {
        return eventRegistrationRepository.findByProfileIdAndEventPostId(profileId, eventPostId)
                .map(mapper::toEventRegistration);
    }

}
