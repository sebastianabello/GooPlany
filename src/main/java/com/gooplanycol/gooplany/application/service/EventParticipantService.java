package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventParticipantInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventParticipantOutputPort;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipantService implements EventParticipantInputPort {

    private final EventParticipantOutputPort eventParticipantOutputPort;

    @Override
    public EventParticipantResponse save(EventParticipantRequest eventParticipant) {
        return eventParticipantOutputPort.save(eventParticipant);
    }

    @Override
    public EventParticipantResponse edit(EventParticipantRequest eventParticipant, Long id) {
        return eventParticipantOutputPort.edit(eventParticipant, id);
    }

    @Override
    public EventParticipantResponse changeStatus(String status, Long id) {
        return eventParticipantOutputPort.changeStatus(status, id);
    }

    @Override
    public List<EventParticipantResponse> findAll(Integer offset, Integer pageSize) {
        return eventParticipantOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventParticipantResponse findById(Long id) {
        return eventParticipantOutputPort.findById(id);
    }

    @Override
    public boolean remove(Long id) {
        return eventParticipantOutputPort.remove(id);
    }

    @Override
    public List<EventParticipantResponse> findEventParticipantsByStatus(Integer offset, Integer pageSize, String statusEventParticipant) {
        return eventParticipantOutputPort.findEventParticipantsByStatus(offset, pageSize, statusEventParticipant);
    }

    @Override
    public CustomerResponse findCustomerEventParticipant(Long id) {
        return eventParticipantOutputPort.findCustomerEventParticipant(id);
    }

    @Override
    public CreditCardResponse findCardEventParticipant(Long id) {
        return eventParticipantOutputPort.findCardEventParticipant(id);
    }

}
