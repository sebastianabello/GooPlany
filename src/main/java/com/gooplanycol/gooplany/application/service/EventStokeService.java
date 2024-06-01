package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventStokeInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventStokeOutputPort;
import com.gooplanycol.gooplany.domain.model.EventStoke;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventStokeService implements EventStokeInputPort {

    private final EventStokeOutputPort eventStokeOutputPort;

    @Override
    public EventStoke save(EventStoke eventStoke) {
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
    public EventStoke edit(EventStoke eventStoke, Long id) {
        return eventStokeOutputPort.edit(eventStoke, id);
    }

    @Override
    public List<EventStoke> findAll(Integer offset, Integer pageSize) {
        return eventStokeOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventStoke findEventPostById(Long id) {
        return eventStokeOutputPort.findEventPostById(id);
    }

    @Override
    public List<EventStoke> findEventStokesByStatusEventPost(String status, Integer offset, Integer pageSize) {
        return eventStokeOutputPort.findEventStokesByStatusEventPost(status, offset, pageSize);
    }

    @Override
    public EventStoke findProductStockByTitle(String title) {
        return eventStokeOutputPort.findProductStockByTitle(title);
    }

    @Override
    public EventStoke changeStatus(String status, Long id) {
        return eventStokeOutputPort.changeStatus(status, id);
    }

}
