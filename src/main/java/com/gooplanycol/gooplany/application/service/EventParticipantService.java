package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventParticipantInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventParticipantOutputPort;
import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipantService implements EventParticipantInputPort {

    private final EventParticipantOutputPort eventParticipantOutputPort;

    @Override
    public EventParticipant save(EventParticipant eventParticipant) {
        return eventParticipantOutputPort.save(eventParticipant);
    }

    @Override
    public EventParticipant edit(EventParticipant eventParticipant, Long id) {
        return eventParticipantOutputPort.edit(eventParticipant, id);
    }

    @Override
    public EventParticipant changeStatus(String status, Long id) {
        return eventParticipantOutputPort.changeStatus(status, id);
    }

    @Override
    public List<EventParticipant> findAll(Integer offset, Integer pageSize) {
        return eventParticipantOutputPort.findAll(offset, pageSize);
    }

    @Override
    public EventParticipant findById(Long id) {
        return eventParticipantOutputPort.findById(id);
    }

    @Override
    public boolean remove(Long id) {
        return eventParticipantOutputPort.remove(id);
    }

    @Override
    public List<EventParticipant> findEventParticipantsByStatus(Integer offset, Integer pageSize, String statusEventParticipant) {
        return eventParticipantOutputPort.findEventParticipantsByStatus(offset, pageSize, statusEventParticipant);
    }

    @Override
    public Customer findCustomerEventParticipant(Long id) {
        return eventParticipantOutputPort.findCustomerEventParticipant(id);
    }

    @Override
    public CreditCard findCardEventParticipant(Long id) {
        return eventParticipantOutputPort.findCardEventParticipant(id);
    }
}
