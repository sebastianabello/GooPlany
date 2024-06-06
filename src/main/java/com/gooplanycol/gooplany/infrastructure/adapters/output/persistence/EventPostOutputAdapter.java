package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.request.EventPostRequest;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventPostOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventPostOutputAdapter implements EventPostOutputPort {

    private final EventPostRepository eventPostRepository;
    private final EventPostOutputMapper eventPostOutputMapper;


    @Override
    public EventPostResponse save(EventPostRequest eventPost) {
        if (eventPost != null) {
            EventPost eventPostSaved = EventPost.builder()
                    .title(eventPost.title())
                    .description(eventPost.description())
                    .eventCategory(typeEventCategory(eventPost.eventCategory()))
                    .typeOfAudience(typeOfAudience(eventPost.typeOfAudience()))
                    .typeOfPlace(typeOfPlace(eventPost.typeOfPlace()))
                    .free(eventPost.free())
                    .price(eventPost.price())
                    .capacity(eventPost.capacity())
                    .startAt(eventPost.startAt())
                    .finishAt(eventPost.finishAt())
                    .address(new Address(null, eventPost.address().street(), eventPost.address().country(), eventPost.address().postalCode()))
                    .build();
            return eventPostOutputMapper.toEventPostResponse(eventPostRepository.save(eventPostSaved));
        } else {
            throw new EventPostException("The event post to save is null");
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
    public EventPostResponse edit(EventPostRequest eventPost, Long id) {
        EventPost e = eventPostRepository.findById(id).orElse(null);
        if (e != null) {
            e.setDescription(eventPost.description());
            e.setEventCategory(typeEventCategory(eventPost.eventCategory()));
            e.setTypeOfAudience(typeOfAudience(eventPost.typeOfAudience()));
            e.setTypeOfPlace(typeOfPlace(eventPost.typeOfPlace()));
            e.setStartAt(eventPost.startAt());
            e.setFinishAt(eventPost.finishAt());
            return eventPostOutputMapper.toEventPostResponse(eventPostRepository.save(e));
        } else {
            throw new EventPostException("The event post to update doesn't exist");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (eventPostRepository.existsById(id)) {
            eventPostRepository.deleteById(id);
            return true;
        } else {
            throw new EventPostException("The event post to delete doesn't exist");
        }
    }

    @Override
    public EventPostResponse findById(Long id) {
        EventPost eventPost = eventPostRepository.findById(id).orElse(null);
        if (eventPost != null) {
            return eventPostOutputMapper.toEventPostResponse(eventPost);
        } else {
            throw new EventPostException("The event post fetched by id doesn't exist");
        }
    }

    @Override
    public List<EventPostResponse> findAll(Integer offset, Integer pageSize) {
        Page<EventPost> list = eventPostRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.stream().map(eventPostOutputMapper::toEventPostResponse).collect(Collectors.toList());
        } else {
            throw new EventPostException("The list of event post post is null");
        }
    }

    @Override
    public List<EventPostResponse> findEventPostByTitle(String title, Integer offset, Integer pageSize) {
        Page<EventPost> list = eventPostRepository.findEventPostByTitle(title, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventPostOutputMapper::toEventPostResponse).collect(Collectors.toList());
        } else {
            throw new EventPostException("The list of event ost fetched by barcode is null");
        }
    }
}
