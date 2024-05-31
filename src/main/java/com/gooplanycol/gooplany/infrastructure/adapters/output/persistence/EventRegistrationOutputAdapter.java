package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventRegistrationOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ProfileOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventRegistrationRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ProfileRepository;
import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class EventRegistrationOutputAdapter implements EventRegistrationOutputPort {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRegistrationOutputMapper eventRegistrationOutputMapper;

    private final ProfileRepository profileRepository;
    private final ProfileOutputMapper profileOutputMapper;

    @Override
    public Optional<EventRegistration> findById(Long id) {
        EventRegistration eventRegistration = eventRegistrationOutputMapper.toEventRegistration(eventRegistrationRepository.findById(id).orElse(null));
        if (eventRegistration != null) {
            return Optional.of(eventRegistration);
        } else {
            throw new EventPostException("The event registration doesn't exist");
        }
    }

    @Override
    public List<EventRegistration> findAll() {
        List<EventRegistration> eventRegistrations = eventRegistrationOutputMapper.toEventRegistrationList(eventRegistrationRepository.findAll());
        if (eventRegistrations != null) {
            return eventRegistrations;
        } else {
            throw new EventPostException("The event registration doesn't exist");
        }
    }

    @Override
    public Optional<EventRegistration> findByProfileAndEventPostId(Long profileId, Long eventPostId) {
        EventRegistration eventRegistration = eventRegistrationOutputMapper.toEventRegistration(eventRegistrationRepository.findByProfileIdAndEventPostId(profileId, eventPostId).orElse(null));
        if (eventRegistration != null) {
            return Optional.of(eventRegistration);
        } else {
            throw new EventPostException("The event registration doesn't exist");
        }
    }

    @Override
    public EventRegistration save(EventRegistration eventRegistration) {
        if (eventRegistration != null) {
            EventRegistration eventRegistrationSaved = EventRegistration.builder()
                    .statusRegistrationEvent(eventRegistration.StatusRegistrationEvent(eventRegistration.getStatusRegistrationEvent()).name())
                    .registeredAt(LocalDateTime.now())
                    .profile(findProfile(eventRegistration.getCustomer().getId()))
                    .build();
            return eventRegistrationOutputMapper.toEventRegistration(eventRegistrationRepository.save(eventRegistrationOutputMapper.toEventRegistrationEntity(eventRegistrationSaved)));
        } else {
            throw new EventPostException("The event registration to save is null");
        }
    }

    private Customer findProfile(Long id) {
        Customer customer = profileOutputMapper.toProfile(profileRepository.findById(id).orElse(null));
        if (customer != null) {
            return customer;
        } else {
            throw new EventPostException("The profile doesn't exist");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (eventRegistrationRepository.existsById(id)) {
            eventRegistrationRepository.deleteById(id);
            return true;
        } else {
            throw new EventPostException("The event registration to delete doesn't exist");
        }
    }

    @Override
    public boolean isProfileRegisteredEvent(Long profileId, Long eventId) {
        return eventRegistrationRepository.existsByProfileIdAndEventPostId(profileId, eventId);
    }

    @Override
    public List<EventRegistration> findAllByEventPostId(Long eventPostId) {
        List<EventRegistration> eventRegistrations = eventRegistrationOutputMapper.toEventRegistrationList(eventRegistrationRepository.findAllByEventPostId(eventPostId));
        if (eventRegistrations != null) {
            return eventRegistrations;
        } else {
            throw new EventPostException("The event registration doesn't exist");
        }
    }

    @Override
    public List<EventRegistration> findByStatusRegistrationEvent(StatusEventParticipant statusEventParticipant) {
        List<EventRegistration> eventRegistrations = eventRegistrationOutputMapper.toEventRegistrationList(eventRegistrationRepository.findByStatusRegistrationEvent(statusEventParticipant));
        if (eventRegistrations != null) {
            return eventRegistrations;
        } else {
            throw new EventPostException("The event registration doesn't exist");
        }
    }
}
