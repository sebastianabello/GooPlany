package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.domain.model.response.EventStokeResponse;

import java.util.List;

public interface EventStokeInputPort {

    EventStokeResponse save(EventStokeRequest eventStoke);

    boolean validateName(String name);

    boolean remove(Long id);

    EventStokeResponse edit(EventStokeRequest eventStoke, Long id);

    List<EventStokeResponse> findAll(Integer offset, Integer pageSize);

    EventStokeResponse findEventPostById(Long id);

    List<EventStokeResponse> findEventStokesByStatusEventPost(String status, Integer offset, Integer pageSize);

    EventStokeResponse findEventStockByTitle(String title);

    EventStokeResponse changeStatus(String status, Long id);

}