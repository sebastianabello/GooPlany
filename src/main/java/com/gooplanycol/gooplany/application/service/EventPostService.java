package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.application.ports.output.*;
import com.gooplanycol.gooplany.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPostService implements EventPostInputPort {

    private final EventPostOutputPort eventPostOutputPort;

    @Override
    public EventPost save(EventPost eventPost) {
        return eventPostOutputPort.save(eventPost);
    }

    @Override
    public EventPost edit(EventPost eventPost, Long id) {
        return eventPostOutputPort.edit(eventPost, id);
    }

    @Override
    public boolean remove(Long id) {
        return eventPostOutputPort.remove(id);
    }


    @Override
    public List<EventPost> findAll(Integer offset, Integer pageSize) {
        return eventPostOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<EventPost> findEventPostByTitle(String title, Integer offset, Integer pageSize) {
        return eventPostOutputPort.findEventPostByTitle(title, offset, pageSize);
    }

    @Override
    public EventPost findById(Long id) {
        return eventPostOutputPort.findById(id);
    }

}
