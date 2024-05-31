package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.EventParticipant;

import java.util.List;

public interface EventParticipantOutputPort {

    EventParticipant save(EventParticipant eventParticipant);

    EventParticipant edit(EventParticipant eventParticipant, Long id);

    EventParticipant changeStatus(String status, Long id);

    List<EventParticipant> findAll(Integer offset, Integer pageSize);

    EventParticipant findById(Long id);

    boolean remove(Long id);

    List<EventParticipant> findEventParticipantsByStatus(Integer offset, Integer pageSize, String statusEventParticipant);

    Customer findCustomerEventParticipant(Long id);

    CreditCard findCardEventParticipant(Long id);
}
