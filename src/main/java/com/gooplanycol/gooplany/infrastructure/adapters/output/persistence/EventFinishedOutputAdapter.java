package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventFinishedOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventFinishedException;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipantEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CreditCardRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventFinishedRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
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
    private final CustomerOutputMapper customerOutputMapper;

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutPutMapper creditCardOutPutMapper;

    private final EventParticipantOutputMapper eventParticipantOutputMapper;

    private final EventPostRepository eventPostRepository;
    private final EventPostOutputMapper eventPostOutputMapper;

    public EventParticipant existEventParticipant(EventParticipant eventParticipant) {
        if (eventParticipant != null) {
            return EventParticipant.builder()
                    .statusRegistration(eventParticipant.knowStatus(eventParticipant.getStatusRegistration().name()))
                    .registeredAt(LocalDateTime.now())
                    .customer(findCustomer(eventParticipant.getId()))
                    .creditCard(findCreditCard(eventParticipant.getId()))
                    .build();
        } else {
            return null;
        }
    }

    public Customer findCustomer(Long id) {
        return customerOutputMapper.toCustomer(customerRepository.findById(id).orElse(null));
    }

    public CreditCard findCreditCard(Long id) {
        return creditCardOutPutMapper.toCreditCard(creditCardRepository.findById(id).orElse(null));
    }

    @Override
    public EventFinished save(EventFinished eventFinished) {
        if (eventFinished != null) {
            EventFinishedEntity eventFinishedEntity;
            if (eventFinished.getEventPosts() != null) {
                eventFinishedEntity = EventFinishedEntity.builder()
                        .concept(eventFinished.getConcept())
                        .eventPosts(eventPostOutputMapper.toEventPostEntityList(eventFinished.eventsPost(eventFinished.getEventPosts())))
                        .createAt(LocalDateTime.now())
                        .build();
            } else {
                eventFinishedEntity = EventFinishedEntity.builder()
                        .concept(eventFinished.getConcept())
                        .eventPosts(new ArrayList<>())
                        .createAt(LocalDateTime.now())
                        .build();
            }
            EventParticipantEntity eventParticipantEntity = eventParticipantOutputMapper.toEventParticipantEntity(existEventParticipant(eventFinished.getEventParticipant()));
            eventFinishedEntity.setEventParticipants(eventParticipantEntity);
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
            eventFinishedEntity.setEventPosts(eventPostOutputMapper.toEventPostEntityList(eventFinished.eventsPost(eventFinished.getEventPosts())));
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The event finished fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public EventFinished findById(Long id) {
        return eventFinishedRepository.findById(id)
                .map(eventFinishedOutputMapper::toEventFinished)
                .orElseThrow(() -> new EventFinishedException("The event finished fetched doesn't exist"));
    }

    @Override
    public List<EventFinished> findAll(Integer offset, Integer pageSize) {
        Page<EventFinishedEntity> list = eventFinishedRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(eventFinishedOutputMapper::toEventFinished).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFinished addEventPost(EventPost eventPost, Long id) {
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(id).orElse(null);
        if (eventFinishedEntity != null) {
            EventPostEntity eventPostEntity = new EventPostEntity(
                    null,
                    eventPost.getTitle(),
                    eventPost.getDescription(),
                    eventPost.getEventCategory(),
                    eventPost.getTypeOfAudience(),
                    eventPost.getTypeOfPlace(),
                    eventPost.getIsFree(),
                    eventPost.getPrice(),
                    eventPost.getIsUnlimited(),
                    eventPost.getCapacity(),
                    eventPost.getStartAt(),
                    eventPost.getFinishAt(),
                    eventPost.getAddress() != null ? new AddressEntity(null, eventPost.getAddress().getStreet(), eventPost.getAddress().getCountry(), eventPost.getAddress().getPostalCode()) : null
            );
            eventFinishedEntity.getEventPosts().add(eventPostEntity);
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The event finished fetched to add event post doesn't exist");
        }
    }

    @Override
    @Transactional
    public EventFinished removeEventPost(Long eventPostId, Long eventFinishedId) {
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(eventFinishedId).orElse(null);
        EventPostEntity eventPostEntity = eventPostRepository.findById(eventPostId).orElse(null);
        if (eventFinishedEntity != null && eventPostEntity != null) {
            eventFinishedEntity.getEventPosts().remove(eventPostEntity);
            return eventFinishedOutputMapper.toEventFinished(eventFinishedRepository.save(eventFinishedEntity));
        } else {
            throw new EventFinishedException("The event finished fetched to remove event post doesn't exist");
        }
    }

    @Override
    public List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize) {
        if (eventFinishedRepository.existsById(id)) {
            Page<EventPostEntity> list = eventFinishedRepository.findEventFinishedEventsPost(id, PageRequest.of(offset, pageSize));
            if (list != null) {
                return list.stream().map(eventPostOutputMapper::toEventPost).collect(Collectors.toList());
            } else {
                throw new EventFinishedException("The list of event post is null");
            }
        } else {
            throw new EventFinishedException("The event finished fetched to find event post doesn't exist");
        }
    }

    @Override
    public EventParticipant findEventParticipant(Long id) {
        EventParticipantEntity eventParticipantEntity = eventFinishedRepository.findEventParticipantEventFinished(id).orElse(null);
        if (eventParticipantEntity != null) {
            return eventParticipantOutputMapper.toEventParticipant(eventParticipantEntity);
        } else {
            throw new EventFinishedException("The event participant fetched doesn't exist");
        }
    }
}
