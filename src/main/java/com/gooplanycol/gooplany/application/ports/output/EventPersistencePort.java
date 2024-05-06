package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Event;

import java.util.List;

public interface EventPersistencePort {

    Event findById(Long id);

    List<Event> findAll();

    Event save(Event event);

    void deleteById(Long id);

}
