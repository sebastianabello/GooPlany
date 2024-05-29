package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.application.ports.output.*;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.*;
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
                        eventPostFound.setEventCategory(eventPost.findEventCategory(eventPost.getEventCategory()).name());
                        eventPostFound.setTypeOfAudience(eventPost.findTypeOfAudience(eventPost.getTypeOfAudience()).name());
                        eventPostFound.setTypeOfPlace(eventPost.findTypeOfPlace(eventPost.getTypeOfPlace()).name());
                        eventPostFound.setStartAt(eventPost.getStartAt());
                        eventPostFound.setFinishAt(eventPost.getFinishAt());
                        eventPostFound.setStatusEventPost(eventPost.findStatusEventPost(eventPost.getStatusEventPost()).name());
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

    @Override
    public boolean remove(Long id) {
        return eventPostOutputPort.remove(id);
    }

    @Override
    public List<EventPost> findByCompanyId(Long id) {
        return eventPostOutputPort.findByCompanyId(id);
    }

    @Override
    public List<EventRegistration> findProfilesByEventId(Long id) {
        return eventPostOutputPort.findProfilesByEventId(id);
    }

    @Override
    public EventPost changeStatus(String status, Long id) {
        return eventPostOutputPort.findById(id)
                .map(eventPost -> {
                    eventPost.setStatusEventPost(eventPost.findStatusEventPost(status).name());
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
