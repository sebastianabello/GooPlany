package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Event;

import java.util.List;

public interface EventServicePort {

    Event findById(Long id);

    List<Event> findAll();

    Event save(Event event);

    Event update(Long id, Event event);

    void deleteById(Long id);
}
