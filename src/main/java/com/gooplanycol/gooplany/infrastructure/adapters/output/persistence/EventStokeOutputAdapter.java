package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventStokeOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventStokeException;
import com.gooplanycol.gooplany.domain.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.domain.model.response.EventStokeResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventStoke;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventStokeOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventStokeRepository;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
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
    public EventStokeResponse save(EventStokeRequest eventStoke) {
        if (eventStoke != null) {
            if (validateName(eventStoke.title())) {
                throw new EventStokeException("Already exist a event with that name");
            } else {
                EventStoke e = EventStoke.builder()
                        .title(eventStoke.title())
                        .description(eventStoke.description())
                        .eventCategory(typeEventCategory(eventStoke.eventCategory()))
                        .typeOfAudience(typeOfAudience(eventStoke.typeOfAudience()))
                        .typeOfPlace(typeOfPlace(eventStoke.typeOfPlace()))
                        .free(eventStoke.free())
                        .price(eventStoke.price())
                        .capacity(eventStoke.capacity())
                        .startAt(eventStoke.startAt())
                        .finishAt(eventStoke.finishAt())
                        .enableEvent(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                return eventStokeOutputMapper.toEventStokeResponse(eventStokeRepository.save(e));
            }
        } else {
            throw new EventStokeException("There not data, the request is null");
        }
    }



    private EventCategory typeEventCategory(String evenCategory) {
        return switch (evenCategory) {
            case "MUSIC" -> EventCategory.MUSIC;
            case "THEATER" -> EventCategory.THEATER;
            case "SPORTS" -> EventCategory.SPORTS;
            case "EXHIBITION" -> EventCategory.EXHIBITION;
            case "CONFERENCE" -> EventCategory.CONFERENCE;
            case "WORKSHOP" -> EventCategory.WORKSHOP;
            case "PARTY" -> EventCategory.PARTY;
            case "FESTIVAL" -> EventCategory.FESTIVAL;
            case "CULTURAL" -> EventCategory.CULTURAL;
            case "RELIGIOUS" -> EventCategory.RELIGIOUS;
            case "CHARITY" -> EventCategory.CHARITY;
            case "OTHER" -> EventCategory.OTHER;
            default -> EventCategory.NOT_SPECIFIED;
        };
    }

    private TypeOfAudience typeOfAudience(String type) {
        return switch (type) {
            case "CHILDREN" -> TypeOfAudience.CHILDREN;
            case "TEENAGERS" -> TypeOfAudience.TEENAGERS;
            case "ADULTS" -> TypeOfAudience.ADULTS;
            case "ELDERLY" -> TypeOfAudience.ELDERLY;
            case "FAMILY" -> TypeOfAudience.FAMILY;
            case "STUDENTS" -> TypeOfAudience.STUDENTS;
            case "PROFESSIONALS" -> TypeOfAudience.PROFESSIONALS;
            case "TOURISTS" -> TypeOfAudience.TOURISTS;
            case "LOCALS" -> TypeOfAudience.LOCALS;
            case "FOREIGNERS" -> TypeOfAudience.FOREIGNERS;
            default -> TypeOfAudience.NOT_SPECIFIED;
        };
    }

    private TypeOfPlace typeOfPlace(String type) {
        return switch (type) {
            case "PARK" -> TypeOfPlace.PARK;
            case "BAR" -> TypeOfPlace.BAR;
            case "SHOPPING_CENTER" -> TypeOfPlace.SHOPPING_CENTER;
            case "RESTAURANT" -> TypeOfPlace.RESTAURANT;
            case "CAFE" -> TypeOfPlace.CAFE;
            case "MUSEUM" -> TypeOfPlace.MUSEUM;
            case "LIBRARY" -> TypeOfPlace.LIBRARY;
            case "GYM" -> TypeOfPlace.GYM;
            case "THEATER" -> TypeOfPlace.THEATER;
            case "CONCERT_HALL" -> TypeOfPlace.CONCERT_HALL;
            case "BEACH" -> TypeOfPlace.BEACH;
            case "HOTEL" -> TypeOfPlace.HOTEL;
            case "STADIUM" -> TypeOfPlace.STADIUM;
            case "UNIVERSITY" -> TypeOfPlace.UNIVERSITY;
            case "SCHOOL" -> TypeOfPlace.SCHOOL;
            case "CHURCH" -> TypeOfPlace.CHURCH;
            default -> TypeOfPlace.NOT_SPECIFIED;
        };
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
            throw new EventStokeException("The event to delete doesn't exist");
        }
    }

    @Override
    public EventStokeResponse edit(EventStokeRequest eventStoke, Long id) {
        EventStoke eventStokeEdit = eventStokeRepository.findById(id).orElse(null);
        if (eventStoke != null && eventStokeEdit != null) {
            eventStokeEdit.setDescription(eventStoke.description());
            eventStokeEdit.setEventCategory(typeEventCategory(eventStoke.eventCategory()));
            eventStokeEdit.setTypeOfAudience(typeOfAudience(eventStoke.typeOfAudience()));
            eventStokeEdit.setTypeOfPlace(typeOfPlace(eventStoke.typeOfPlace()));
            eventStokeEdit.setStartAt(eventStoke.startAt());
            eventStokeEdit.setFinishAt(eventStoke.finishAt());
            eventStokeEdit.setEnableEvent(eventStoke.enableEvent());
            eventStokeEdit.setUpdatedAt(LocalDateTime.now());
            return eventStokeOutputMapper.toEventStokeResponse(eventStokeRepository.save(eventStokeEdit));
        } else {
            throw new EventStokeException("The event to update doesn't exist or the request is null");
        }
    }

    @Override
    public List<EventStokeResponse> findAll(Integer offset, Integer pageSize) {
        Page<EventStoke> list = eventStokeRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(eventStokeOutputMapper::toEventStokeResponse).collect(Collectors.toList());
    }

    @Override
    public EventStokeResponse findEventPostById(Long id) {
        EventStoke eventStoke = eventStokeRepository.findById(id).orElse(null);
        if (eventStoke != null) {
            return eventStokeOutputMapper.toEventStokeResponse(eventStoke);
        } else {
            throw new EventStokeException("The event searched doesn't exist");
        }
    }

    @Override
    public List<EventStokeResponse> findEventStokesByEnableEventPost(String status, Integer offset, Integer pageSize) {
        Page<EventStoke> list = eventStokeRepository.findEventStokesByEnableEventPost(PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventStokeOutputMapper::toEventStokeResponse).collect(Collectors.toList());
        } else {
            throw new EventStokeException("The list of events is null");
        }
    }


    @Override
    public EventStokeResponse findEventStockByTitle(String title) {
        EventStoke eventStoke = eventStokeRepository.findEventStockByTitle(title).orElse(null);
        if (eventStoke != null) {
            return eventStokeOutputMapper.toEventStokeResponse(eventStoke);
        } else {
            throw new EventStokeException("The event searched doesn't exist");
        }
    }

    @Override
    public EventStokeResponse participateEvent(int amount, Long id) {
        EventStoke eventStoke = eventStokeRepository.findById(id).orElse(null);
        if ( eventStoke != null) {
            int stockCapacity = eventStoke.getCapacity();
            if (stockCapacity >= amount) {
                eventStoke.setCapacity(stockCapacity - amount);
                return eventStokeOutputMapper.toEventStokeResponse(eventStokeRepository.save(eventStoke));
            } else {
                throw new EventStokeException("The amount of participants is greater than the capacity of the event");
            }
        } else {
            throw new EventStokeException("The event to participate doesn't exist");
        }
    }

}
