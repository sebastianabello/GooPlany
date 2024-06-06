package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventStokeInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventStokeOutputPort;
import com.gooplanycol.gooplany.domain.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.domain.model.response.EventStokeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventStokeService implements EventStokeInputPort {

    private final EventStokeOutputPort eventStokeOutputPort;

    @Override
    public EventStokeResponse save(EventStokeRequest eventStoke) {
        return eventStokeOutputPort.save(eventStoke);
    }

    @Override
    public boolean validateName(String name) {
        return eventStokeOutputPort.validateName(name);
    }

    @Override
    public boolean remove(Long id) {
        return eventStokeOutputPort.remove(id);
    }

    @Override
    public EventStokeResponse edit(EventStokeRequest eventStoke, Long id) {
        return eventStokeOutputPort.edit(eventStoke, id);
    }

    @Override
    public List<EventStokeResponse> findAll(Integer offset, Integer pageSize) {
        return eventStokeOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventStokeResponse findEventPostById(Long id) {
        return eventStokeOutputPort.findEventPostById(id);
    }

    @Override
    public List<EventStokeResponse> findEventStokesByEnableEventPost(String status, Integer offset, Integer pageSize) {
        return eventStokeOutputPort.findEventStokesByEnableEventPost(status, offset, pageSize);
    }

    @Override
    public EventStokeResponse findEventStockByTitle(String title) {
        return eventStokeOutputPort.findEventStockByTitle(title);
    }

    @Override
    public EventStokeResponse participateEvent(int amount, Long id) {
        return eventStokeOutputPort.participateEvent(amount, id);
    }

}
