package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventFinishedOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventFinishedException;
import com.gooplanycol.gooplany.domain.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.request.EventPostRequest;
import com.gooplanycol.gooplany.domain.model.response.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventFinishedOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventParticipantOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventPostOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.*;
import com.gooplanycol.gooplany.utils.StatusEventParticipant;
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

    private final EventParticipantRepository eventParticipantRepository;
    private final EventParticipantOutputMapper eventParticipantOutputMapper;

    private final EventPostOutputMapper eventPostOutputMapper;

    private EventPost existEventPost(EventPostRequest eventPost) {
        if (eventPost != null) {
            return EventPost.builder()
                    .title(eventPost.title())
                    .description(eventPost.description())
                    .free(eventPost.free())
                    .price(eventPost.price())
                    .capacity(eventPost.capacity())
                    .startAt(eventPost.startAt())
                    .finishAt(eventPost.finishAt())
                    .build();
        }
        return null;
    }

    private StatusEventParticipant knowStatusParticipant(String status) {
        return switch (status.toLowerCase()) {
            case "registered" -> StatusEventParticipant.REGISTERED;
            case "canceled" -> StatusEventParticipant.CANCELED;
            default -> StatusEventParticipant.PENDING;
        };
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    private CreditCard findCard(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    private List<EventParticipant> eventParticipant(List<EventParticipantRequest> list) {
        if (list != null) {
            return list.stream().map(eventParticipant -> new EventParticipant(
                    null,
                    knowStatusParticipant(eventParticipant.statusRegistration()),
                    eventParticipant.createAt(),
                    findCustomer(eventParticipant.customer().id()),
                    findCard(eventParticipant.card().id())
            )).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public EventFinishedResponse save(EventFinishedRequest eventFinished) {
        if (eventFinished != null) {
            EventFinished eventFinishedEntity;
            if (eventFinished.eventParticipants() != null) {
                eventFinishedEntity = EventFinished.builder()
                        .createAt(LocalDateTime.now())
                        .eventParticipants(eventParticipant(eventFinished.getRegisteredParticipants()))
                        .build();
            } else {
                eventFinishedEntity = EventFinished.builder()
                        .eventParticipants(new ArrayList<>())
                        .createAt(LocalDateTime.now())
                        .build();
            }

            EventPost eventPostEntity = existEventPost(eventFinished.eventPost());
            eventFinishedEntity.setEventPost(eventPostEntity);
            return eventFinishedOutputMapper.toEventFinishedResponse(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The request to save is null");
        }
    }

    @Override
    public EventFinishedResponse edit(EventFinishedRequest eventFinished, Long id) {
        EventFinished eventFinishedEntity = eventFinishedRepository.findById(id).orElse(null);
        if (eventFinishedEntity != null && eventFinished != null) {
            eventFinishedEntity.setEventPost(existEventPost(eventFinished.eventPost()));
            eventFinishedEntity.setEventParticipants(eventParticipant(eventFinished.eventParticipants()));
            return eventFinishedOutputMapper.toEventFinishedResponse(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The request to edit is null");
        }
    }

    @Override
    public EventFinishedResponse findById(Long id) {
        if (eventFinishedRepository.existsById(id)) {
            return eventFinishedOutputMapper.toEventFinishedResponse(eventFinishedRepository.findById(id).orElse(null));
        } else {
            throw new EventFinishedException("The event finished with id " + id + " not found");
        }
    }

    @Override
    public List<EventFinishedResponse> findAll(Integer offset, Integer pageSize) {
        Page<EventFinished> list = eventFinishedRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(eventFinishedOutputMapper::toEventFinishedResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFinishedResponse addEventParticipant(EventParticipantRequest eventParticipant, Long id) {
        EventFinished eventFinishedEntity = eventFinishedRepository.findById(id).orElse(null);
        if (eventFinishedEntity != null) {
            EventParticipant eventParticipantEntity = new EventParticipant(
                    null,
                    knowStatusParticipant(eventParticipant.statusRegistration()),
                    eventParticipant.createAt(),
                    findCustomer(eventParticipant.customer().id()),
                    findCard(eventParticipant.card().id())
            );
            eventFinishedEntity.getEventParticipants().add(eventParticipantEntity);
            return eventFinishedOutputMapper.toEventFinishedResponse(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The Event finished fetched to add it a new participant doesn't exist");
        }
    }

    @Override
    @Transactional
    public EventFinishedResponse removeEventParticipant(Long eventParticipantId, Long eventFinishedId) {
        EventFinished eventFinishedEntity = eventFinishedRepository.findById(eventFinishedId).orElse(null);
        EventParticipant eventParticipantEntity = eventParticipantRepository.findById(eventParticipantId).orElse(null);
        if (eventFinishedEntity != null && eventParticipantEntity != null) {
            eventFinishedEntity.getEventParticipants().remove(eventParticipantEntity);
            return eventFinishedOutputMapper.toEventFinishedResponse(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The event finished fetched to remove a participant doesn't exist");
        }
    }

    @Override
    public List<EventParticipantResponse> findEventParticipants(Long id, Integer offset, Integer pageSize) {
        if (eventFinishedRepository.existsById(id)) {
        Page<EventParticipant> list = eventFinishedRepository.findEventFinishedEventParticipant(id, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventParticipantOutputMapper::toEventParticipantResponse).collect(Collectors.toList());
        } else {
            throw new EventFinishedException("The list of participants is null");
        }
        } else {
            throw new EventFinishedException("The event finished with id " + id + " not found");
        }

    }

    @Override
    public EventPostResponse findEventPost(Long id) {
        EventPost eventPostEntity = eventFinishedRepository.findEventPostEventFinished(id).orElse(null);
        if (eventPostEntity != null) {
            return eventPostOutputMapper.toEventPostResponse(eventPostEntity);
        } else {
            throw new EventFinishedException("The event post fetched by id doesn't exist");
        }
    }
}
