package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.domain.model.request.EventPostRequest;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPostService implements EventPostInputPort {

    private final EventPostOutputPort eventPostOutputPort;

    @Override
    public EventPostResponse save(EventPostRequest eventPost) {
        return eventPostOutputPort.save(eventPost);
    }

    @Override
    public EventPostResponse edit(EventPostRequest eventPost, Long id) {
        return eventPostOutputPort.edit(eventPost, id);
    }

    @Override
    public boolean remove(Long id) {
        return eventPostOutputPort.remove(id);
    }

    @Override
    public List<EventPostResponse> findAll(Integer offset, Integer pageSize) {
        return eventPostOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<EventPostResponse> findEventPostByTitle(String title, Integer offset, Integer pageSize) {
        return eventPostOutputPort.findEventPostByTitle(title, offset, pageSize);
    }

    @Override
    public EventPostResponse findById(Long id) {
        return eventPostOutputPort.findById(id);
    }

}
