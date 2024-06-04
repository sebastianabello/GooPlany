package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventFinishedOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventFinishedException;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.*;

import com.gooplanycol.gooplany.utils.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventFinishedOutputAdapter implements EventFinishedOutputPort {

    private final EventFinishedRepository eventFinishedRepository;
    private final EventFinishedOutputMapper eventFinishedOutputMapper;

    private final CustomerRepository customerRepository;

    private final CreditCardRepository creditCardRepository;

    private final AddressRepository addressRepository;

    private final EventParticipantRepository eventParticipantRepository;
    private final EventParticipantOutputMapper eventParticipantOutputMapper;

    private final EventPostOutputMapper eventPostOutputMapper;

    private EventPostEntity existEventPost(EventPost eventPost) {
        if (eventPost != null) {
            return EventPostEntity.builder()
                    .title(eventPost.getTitle())
                    .description(eventPost.getDescription())
                    .eventCategory(eventPost.findEventCategory(eventPost.getEventCategory().name()))
                    .typeOfAudience(eventPost.findTypeOfAudience(eventPost.getTypeOfAudience().name()))
                    .typeOfPlace(eventPost.findTypeOfPlace(eventPost.getTypeOfPlace().name()))
                    .isFree(eventPost.getIsFree())
                    .price(eventPost.getPrice())
                    .isUnlimited(eventPost.getIsUnlimited())
                    .capacity(eventPost.getCapacity())
                    .startAt(eventPost.getStartAt())
                    .finishAt(eventPost.getFinishAt())
                    .address(findAddress(eventPost.getAddress().getId()))
                    .build();
        }
        return null;
    }

    private StatusEventParticipant knowStatusParticipant(String status) {
        return switch (status.toLowerCase()) {
            case "registered" -> StatusEventParticipant.REGISTERED;
            case "canceled" -> StatusEventParticipant.CANCELED;
            default -> StatusEventParticipant.UNREGISTERED;
        };
    }

    private CustomerEntity findCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    private CreditCardEntity findCard(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    private AddressEntity findAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    private List<EventParticipantEntity> eventParticipants(List<EventParticipant> list) {
        if (list != null) {
            return list.stream().map(eventParticipant -> new EventParticipantEntity(
                    null,
                    knowStatusParticipant(eventParticipant.getStatusRegistration().name()),
                    eventParticipant.getRegisteredAt(),
                    findCustomer(eventParticipant.getCustomer().getId()),
                    findCard(eventParticipant.getCreditCard().getId())
            )).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public EventFinished save(EventFinished eventFinished) {
        if (eventFinished != null) {
            EventFinishedEntity eventFinishedEntity;
            if (eventFinished.getEventParticipants() != null) {
                eventFinishedEntity = EventFinishedEntity.builder()
                        .concept(eventFinished.getConcept())
                        .createAt(LocalDateTime.now())
                        .eventParticipants(eventParticipants(eventFinished.getEventParticipants()))
                        .build();
            } else {
                eventFinishedEntity = EventFinishedEntity.builder()
                        .concept(eventFinished.getConcept())
                        .eventParticipants(new ArrayList<>())
                        .createAt(LocalDateTime.now())
                        .build();
            }

            EventPostEntity eventPostEntity = existEventPost(eventFinished.getEventPost());
            eventFinishedEntity.setEventPost(eventPostEntity);
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The request to save is null");
        }
    }

    @Override
    public EventFinished edit(EventFinished eventFinished, Long id) {
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(id).orElse(null);
        if (eventFinishedEntity != null && eventFinished != null) {
            eventFinishedEntity.setConcept(eventFinished.getConcept());
            eventFinishedEntity.setEventPost(existEventPost(eventFinished.getEventPost()));
            eventFinishedEntity.setEventParticipants(eventParticipants(eventFinished.getEventParticipants()));
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The request to edit is null");
        }
    }

    @Override
    public EventFinished findById(Long id) {
        if (eventFinishedRepository.existsById(id)) {
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.findById(id).orElse(null));
        } else {
            throw new EventFinishedException("The event finished with id " + id + " not found");
        }
    }

    @Override
    public List<EventFinished> findAll(Integer offset, Integer pageSize) {
        Page<EventFinishedEntity> list = eventFinishedRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(eventFinishedOutputMapper::toEventFinished).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFinished addEventParticipant(EventParticipant eventParticipant, Long id) {
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(id).orElse(null);
        if (eventFinishedEntity != null) {
            EventParticipantEntity eventParticipantEntity = new EventParticipantEntity(
                    null,
                    knowStatusParticipant(eventParticipant.getStatusRegistration().name()),
                    eventParticipant.getRegisteredAt(),
                    findCustomer(eventParticipant.getCustomer().getId()),
                    findCard(eventParticipant.getCreditCard().getId())
            );
            eventFinishedEntity.getEventParticipants().add(eventParticipantEntity);
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The Event finished fetched to add it a new participant doesn't exist");
        }
    }

    @Override
    @Transactional
    public EventFinished removeEventParticipant(Long eventParticipantId, Long eventFinishedId) {
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(eventFinishedId).orElse(null);
        EventParticipantEntity eventParticipantEntity = eventParticipantRepository.findById(eventParticipantId).orElse(null);
        if (eventFinishedEntity != null && eventParticipantEntity != null) {
            eventFinishedEntity.getEventParticipants().remove(eventParticipantEntity);
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The event finished fetched to remove a participant doesn't exist");
        }
    }

    @Override
    public List<EventParticipant> findEventParticipants(Long id, Integer offset, Integer pageSize) {
        if (eventFinishedRepository.existsById(id)) {
        Page<EventParticipantEntity> list = eventFinishedRepository.findEventFinishedEventParticipant(id, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventParticipantOutputMapper::toEventParticipant).collect(Collectors.toList());
        } else {
            throw new EventFinishedException("The list of participants is null");
        }
        } else {
            throw new EventFinishedException("The event finished with id " + id + " not found");
        }

    }

    @Override
    public EventPost findEventPost(Long id) {
        EventPostEntity eventPostEntity = eventFinishedRepository.findEventPostEventFinished(id).orElse(null);
        if (eventPostEntity != null) {
            return eventPostOutputMapper.toEventPost(eventPostEntity);
        } else {
            throw new EventFinishedException("The event post fetched by id doesn't exist");
        }
    }
}
