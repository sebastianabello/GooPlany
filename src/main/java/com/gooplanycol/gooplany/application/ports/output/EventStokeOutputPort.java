package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.EventStoke;

import java.util.List;

public interface EventStokeOutputPort {

    EventStoke save(EventStoke eventStoke);

    boolean validateNameAndAddress(String name, String address);

    boolean remove(Long id);

    EventStoke edit(EventStoke eventStoke, Long id);

    List<EventStoke> findAll(Integer offset, Integer pageSize);

    EventStoke findEventPostById(Long id);

    List<EventStoke> findEventStokesByEnableEventPost(Integer offset, Integer pageSize);

    EventStoke findProductStockByTitle(String title);

    EventStoke changeStatus(String status, Long id);

    List<EventStoke> findEventStokeByStatus(Integer offset, Integer pageSize, String statusEventPost);

}
