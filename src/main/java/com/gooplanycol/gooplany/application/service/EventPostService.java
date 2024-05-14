package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventPostNotFoundException;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.EventPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventPostService implements EventPostInputPort {

    private final EventPostOutputPort eventPostOutputPort;

    @Override
    public EventPost createEventPost(EventPost eventPost) {
        return eventPostOutputPort.save(eventPost);
    }

    @Override
    public EventPost findById(Long id) {
        return eventPostOutputPort.findById(id)
                .orElseThrow(EventPostNotFoundException::new);
    }

    @Override
    public List<EventPost> findAll() {
        return eventPostOutputPort.findAll();
    }

    @Override
    public EventPost updateEventPost(Long id,EventPost eventPost) {
        return eventPostOutputPort.findById(id)
                .map(eventPostFound -> {
                    eventPostFound.setDescription(eventPost.getDescription());
                    eventPostFound.setEventCategory(eventPost.getEventCategory());
                    eventPostFound.setTypeOfAudience(eventPost.getTypeOfAudience());
                    eventPostFound.setTypeOfPlace(eventPost.getTypeOfPlace());
                    eventPostFound.setStartAt(eventPost.getStartAt());
                    eventPostFound.setFinishAt(eventPost.getFinishAt());
                    return eventPostOutputPort.save(eventPostFound);
                })
                .orElseThrow(EventPostNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        eventPostOutputPort.deleteById(id);
    }

    @Override
    public List<EventPost> findByAddress(Address address) {
        return null;
    }

}
