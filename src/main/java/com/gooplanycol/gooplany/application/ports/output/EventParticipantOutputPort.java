package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;

import java.util.List;

public interface EventParticipantOutputPort {

    EventParticipantResponse save(EventParticipantRequest eventParticipant);

    EventParticipantResponse edit(EventParticipantRequest eventParticipant, Long id);

    EventParticipantResponse changeStatus(String status, Long id);

    List<EventParticipantResponse> findAll(Integer offset, Integer pageSize);

    EventParticipantResponse findById(Long id);

    boolean remove(Long id);

    List<EventParticipantResponse> findEventParticipantsByStatus(Integer offset, Integer pageSize, String statusEventParticipant);

    CustomerResponse findCustomerEventParticipant(Long id);

    CreditCardResponse findCardEventParticipant(Long id);
}
