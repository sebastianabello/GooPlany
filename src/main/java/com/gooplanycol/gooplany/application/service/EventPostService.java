package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.application.ports.output.*;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.StatusEventPost;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPostService implements EventPostInputPort {

    private final EventPostOutputPort eventPostOutputPort;

    private final CompanyOutPort companyOutPort;


    @Override
    public EventPost save(EventPost eventPost) {
        return eventPostOutputPort.save(eventPost);
    }

    @Override
    public EventPost edit(EventPost eventPost, Long id) {
        return eventPostOutputPort.findById(id)
                .map(eventPostFound -> {
                    if (eventPost != null) {
                        eventPostFound.setDescription(eventPost.getDescription());
                        eventPostFound.setEventCategory(findEventCategory(eventPost.getEventCategory()).name());
                        eventPostFound.setTypeOfAudience(findTypeOfAudience(eventPost.getTypeOfAudience()).name());
                        eventPostFound.setTypeOfPlace(findTypeOfPlace(eventPost.getTypeOfPlace()).name());
                        eventPostFound.setStartAt(eventPost.getStartAt());
                        eventPostFound.setFinishAt(eventPost.getFinishAt());
                        eventPostFound.setStatusEventPost(findStatusEventPost(eventPost.getStatusEventPost()).name());
                        eventPostFound.setUpdatedAt(LocalDate.now());
                        eventPostFound.setCompany(findCompany(eventPost.getCompany().getId()));

                    }
                    return eventPostOutputPort.save(eventPostFound);
                })
                .orElseThrow(() -> new EventPostException("The event post to update doesn't exist or the request is null"));
    }

    private Company findCompany(Long id) {
        return companyOutPort.findById(id).orElse(null);
    }

    private EventCategory findEventCategory(String eventCategory) {
        return switch (eventCategory) {
            case "music" -> EventCategory.MUSIC;
            case "theater" -> EventCategory.THEATER;
            case "sports" -> EventCategory.SPORTS;
            case "exhibition" -> EventCategory.EXHIBITION;
            case "conference" -> EventCategory.CONFERENCE;
            case "workshop" -> EventCategory.WORKSHOP;
            case "party" -> EventCategory.PARTY;
            case "festival" -> EventCategory.FESTIVAL;
            case "cultural" -> EventCategory.CULTURAL;
            case "religious" -> EventCategory.RELIGIOUS;
            case "charity" -> EventCategory.CHARITY;
            case "other" -> EventCategory.OTHER;
            default -> EventCategory.NOT_SPECIFIED;
        };
    }

    private TypeOfAudience findTypeOfAudience(String typeOfAudience) {
        return switch (typeOfAudience) {
            case "children" -> TypeOfAudience.CHILDREN;
            case "teenagers" -> TypeOfAudience.TEENAGERS;
            case "adults" -> TypeOfAudience.ADULTS;
            case "elderly" -> TypeOfAudience.ELDERLY;
            case "family" -> TypeOfAudience.FAMILY;
            case "students" -> TypeOfAudience.STUDENTS;
            case "professionals" -> TypeOfAudience.PROFESSIONALS;
            case "tourists" -> TypeOfAudience.TOURISTS;
            case "locals" -> TypeOfAudience.LOCALS;
            case "foreigners" -> TypeOfAudience.FOREIGNERS;
            default -> TypeOfAudience.NOT_SPECIFIED;
        };
    }

    private TypeOfPlace findTypeOfPlace(String typeOfPlace) {
        return switch (typeOfPlace) {
            case "park" -> TypeOfPlace.PARK;
            case "bar" -> TypeOfPlace.BAR;
            case "shopping_center" -> TypeOfPlace.SHOPPING_CENTER;
            case "restaurant" -> TypeOfPlace.RESTAURANT;
            case "cafe" -> TypeOfPlace.CAFE;
            case "museum" -> TypeOfPlace.MUSEUM;
            case "library" -> TypeOfPlace.LIBRARY;
            case "gym" -> TypeOfPlace.GYM;
            case "theater" -> TypeOfPlace.THEATER;
            case "concert_hall" -> TypeOfPlace.CONCERT_HALL;
            case "beach" -> TypeOfPlace.BEACH;
            case "hotel" -> TypeOfPlace.HOTEL;
            case "stadium" -> TypeOfPlace.STADIUM;
            case "university" -> TypeOfPlace.UNIVERSITY;
            case "school" -> TypeOfPlace.SCHOOL;
            case "church" -> TypeOfPlace.CHURCH;
            default -> TypeOfPlace.NOT_SPECIFIED;
        };
    }

    private StatusEventPost findStatusEventPost(String statusEventPost) {
        return switch (statusEventPost.toLowerCase()) {
            case "approved" -> StatusEventPost.APPROVED;
            case "rejected" -> StatusEventPost.REJECTED;
            case "finished" -> StatusEventPost.FINISHED;
            default -> StatusEventPost.PENDING;
        };
    }

    @Override
    public boolean remove(Long id) {
        return eventPostOutputPort.remove(id);
    }

    @Override
    public List<EventPost> findByCompanyId(Long companyId) {
        return eventPostOutputPort.findByCompanyId(companyId);
    }

    @Override
    public List<Profile> findProfilesByEventId(Long eventId) {
        return eventPostOutputPort.findProfilesByEventId(eventId);
    }

    @Override
    public EventPost changeStatus(String status, Long id) {
        return eventPostOutputPort.findById(id)
                .map(eventPost -> {
                    eventPost.setStatusEventPost(findStatusEventPost(status).name());
                    return eventPostOutputPort.save(eventPost);
                })
                .orElseThrow(() -> new EventPostException("The event post fetched to change its status doesn't exist"));
    }

    @Override
    public List<EventPost> findAll(Integer offset, Integer pageSize) {
        return eventPostOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventPost findById(Long id) {
        return eventPostOutputPort.findById(id)
                .orElseThrow(() -> new EventPostException("The event post fetched by id doesn't exist"));
    }

    @Override
    public List<EventPost> findEventPostByStatus(Integer offset, Integer pageSize, String statusEventPost) {
        return eventPostOutputPort.findEventPostByStatus(offset, pageSize, statusEventPost);
    }
}
