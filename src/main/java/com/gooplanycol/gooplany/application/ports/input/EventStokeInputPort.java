package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventStoke;

import java.util.List;

public interface EventStokeInputPort {

    EventStoke save(EventStoke eventStoke);

    boolean validateName(String name);

    boolean remove(Long id);

    EventStoke edit(EventStoke eventStoke, Long id);

    List<EventStoke> findAll(Integer offset, Integer pageSize);

    EventStoke findEventPostById(Long id);

    List<EventStoke> findEventStokesByStatusEventPost(String status, Integer offset, Integer pageSize);

    EventStoke findEventStockByTitle(String title);

    EventStoke changeStatus(String status, Long id);


}
