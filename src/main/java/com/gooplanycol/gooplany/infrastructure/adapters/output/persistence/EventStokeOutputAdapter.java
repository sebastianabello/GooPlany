package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventStokeOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventParticipantException;
import com.gooplanycol.gooplany.domain.exception.EventStokeException;
import com.gooplanycol.gooplany.domain.model.EventStoke;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventStokeEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventStokeOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventStokeRepository;
import com.gooplanycol.gooplany.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventStokeOutputAdapter implements EventStokeOutputPort {

    private final EventStokeRepository eventStokeRepository;
    private final EventStokeOutputMapper eventStokeOutputMapper;

    @Override
    public EventStoke save(EventStoke eventStoke) {
        if (eventStoke != null) {
            if (validateName(eventStoke.getTitle())) {
                throw new EventStokeException("Already exist a event with that name");
            } else {
                EventStokeEntity e = EventStokeEntity.builder()
                        .title(eventStoke.getTitle())
                        .description(eventStoke.getDescription())
                        .eventCategory(eventStoke.findEventCategory(eventStoke.getEventCategory().name()))
                        .typeOfAudience(eventStoke.findTypeOfAudience(eventStoke.getTypeOfAudience().name()))
                        .typeOfPlace(eventStoke.findTypeOfPlace(eventStoke.getTypeOfPlace().name()))
                        .isFree(eventStoke.getIsFree())
                        .price(eventStoke.getPrice())
                        .isUnlimited(eventStoke.getIsUnlimited())
                        .capacity(eventStoke.getCapacity())
                        .startAt(eventStoke.getStartAt())
                        .finishAt(eventStoke.getFinishAt())
                        .statusEventPost(eventStoke.findStatusEventPost(eventStoke.getStatusEventPost().name()))
                        .createdAt(LocalDateTime.now())
                        .address(new AddressEntity(null, eventStoke.getAddress().getStreet(), eventStoke.getAddress().getCountry(), eventStoke.getAddress().getPostalCode()))
                        .build();
                return eventStokeOutputMapper.toEventStoke(eventStokeRepository.save(e));
            }
        } else {
            throw new EventStokeException("There not data, the request is null");
        }
    }

    @Override
    public boolean validateName(String name) {
        return eventStokeRepository.existsByTitle(name);
    }
    @Override
    public boolean remove(Long id) {
        if (eventStokeRepository.existsById(id)) {
            eventStokeRepository.deleteById(id);
            return true;
        } else {
            throw new EventParticipantException("The event to delete doesn't exist");
        }
    }

    @Override
    public EventStoke edit(EventStoke eventStoke, Long id) {
        EventStokeEntity eventStokeEdit = eventStokeRepository.findById(id).orElse(null);
        if (eventStoke != null && eventStokeEdit != null) {
            eventStokeEdit.setDescription(eventStoke.getDescription());
            eventStokeEdit.setEventCategory(eventStoke.findEventCategory(eventStoke.getEventCategory().name()));
            eventStokeEdit.setTypeOfAudience(eventStoke.findTypeOfAudience(eventStoke.getTypeOfAudience().name()));
            eventStokeEdit.setTypeOfPlace(eventStoke.findTypeOfPlace(eventStoke.getTypeOfPlace().name()));
            eventStokeEdit.setStartAt(eventStoke.getStartAt());
            eventStokeEdit.setFinishAt(eventStoke.getFinishAt());
            eventStokeEdit.setStatusEventPost(eventStoke.findStatusEventPost(eventStoke.getStatusEventPost().name()));
            eventStokeEdit.setUpdatedAt(LocalDateTime.now());
            return eventStokeOutputMapper.toEventStoke(eventStokeRepository.save(eventStokeEdit));
        } else {
            throw new EventStokeException("The event to update doesn't exist or the request is null");
        }
    }

    @Override
    public List<EventStoke> findAll(Integer offset, Integer pageSize) {
        Page<EventStokeEntity> list = eventStokeRepository.findAll(PageRequest.of(offset,pageSize));
        return list.stream().map(eventStokeOutputMapper::toEventStoke).collect(Collectors.toList());
    }

    @Override
    public EventStoke findEventPostById(Long id) {
        EventStokeEntity eventStoke = eventStokeRepository.findById(id).orElse(null);
        if(eventStoke!=null){
            return eventStokeOutputMapper.toEventStoke(eventStoke);
        }else{
            throw new EventStokeException("The event searched doesn't exist");
        }
    }

    @Override
    public List<EventStoke> findEventStokesByStatusEventPost(String status, Integer offset, Integer pageSize) {
        Page<EventStokeEntity> list = eventStokeRepository.findEventStokesByStatusEventPost(status, PageRequest.of(offset,pageSize));
        if(list!=null){
            return list.stream().map(eventStokeOutputMapper::toEventStoke).collect(Collectors.toList());
        }else{
            throw new EventStokeException("The list of event is null");
        }
    }

    @Override
    public EventStoke findEventStockByTitle(String title) {
        EventStokeEntity eventStoke = eventStokeRepository.findEventStockByTitle(title).orElse(null);
        if(eventStoke!=null){
            return eventStokeOutputMapper.toEventStoke(eventStoke);
        }else{
            throw new EventStokeException("The event searched doesn't exist");
        }
    }

    @Override
    public EventStoke changeStatus(String status, Long id) {
        EventStokeEntity eventStoke = eventStokeRepository.findById(id).orElse(null);
           if(eventStoke!=null){
                eventStoke.setStatusEventPost(knowStatus(status));
                eventStoke.setUpdatedAt(LocalDateTime.now());
                return eventStokeOutputMapper.toEventStoke(eventStokeRepository.save(eventStoke));
            }else{
                throw new EventStokeException("The event fetched to change its status doesn't exist");
           }
    }

    public StatusEventPost knowStatus(String status) {
        return switch (status.toLowerCase()) {
            case "approved" -> StatusEventPost.APPROVED;
            case "rejected" -> StatusEventPost.REJECTED;
            case "finished" -> StatusEventPost.FINISHED;
            default -> StatusEventPost.PENDING;
        };
    }
}
