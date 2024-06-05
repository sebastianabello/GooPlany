package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventParticipantOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventParticipantException;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipant;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventParticipantOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CreditCardRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventParticipantRepository;
import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventParticipantOutputAdapter implements EventParticipantOutputPort {

    private final EventParticipantRepository eventParticipantRepository;
    private final EventParticipantOutputMapper eventParticipantOutputMapper;

    private final CustomerRepository customerRepository;
    private final CustomerOutputMapper customerOutputMapper;

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutputMapper creditCardOutPutMapper;


    @Override
    public EventParticipantResponse save(EventParticipantRequest eventParticipant) {
        if (eventParticipant != null) {
            EventParticipant e = EventParticipant.builder()
                    .statusRegistration(knowStatus(eventParticipant.statusRegistration()))
                    .registeredAt(LocalDateTime.now())
                    .customer(findCustomer(eventParticipant.customer().id()))
                    .creditCard(findCreditCard(eventParticipant.card().id()))
                    .build();
            return eventParticipantOutputMapper.toEventParticipantResponse(eventParticipantRepository.save(e));
        } else {
            throw new EventParticipantException("The request to save is null");
        }
    }

    public StatusEventParticipant knowStatus(String status) {
        return switch (status.toLowerCase()) {
            case "registered" -> StatusEventParticipant.REGISTERED;
            case "canceled" -> StatusEventParticipant.CANCELED;
            default -> StatusEventParticipant.UNREGISTERED;
        };
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    private CreditCard findCreditCard(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public EventParticipantResponse edit(EventParticipantRequest eventParticipant, Long id) {
        EventParticipant e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null && eventParticipant != null) {
            e.setStatusRegistration(knowStatus(eventParticipant.statusRegistration()));
            e.setRegisteredAt(LocalDateTime.now());
            e.setCustomer(findCustomer(eventParticipant.customer().id()));
            e.setCreditCard(findCreditCard(eventParticipant.card().id()));
            e = eventParticipantRepository.save(e);
            return eventParticipantOutputMapper.toEventParticipantResponse(e);
        } else {
            throw new EventParticipantException("The event participant fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public EventParticipantResponse changeStatus(String status, Long id) {
        EventParticipant e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null) {
            e.setStatusRegistration(knowStatus(status));
            e = eventParticipantRepository.save(e);
            return eventParticipantOutputMapper.toEventParticipantResponse(e);
        } else {
            throw new EventParticipantException("The event participant fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public List<EventParticipantResponse> findAll(Integer offset, Integer pageSize) {
        Page<EventParticipant> list = eventParticipantRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(eventParticipantOutputMapper::toEventParticipantResponse).collect(Collectors.toList());
    }

    @Override
    public EventParticipantResponse findById(Long id) {
        EventParticipant e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null) {
            return eventParticipantOutputMapper.toEventParticipantResponse(e);
        } else {
            throw new EventParticipantException("The event participant fetched by id doesn't exist");
        }
    }

    @Override
    public boolean remove(Long id) {
        EventParticipant e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null) {
            eventParticipantRepository.delete(e);
            return true;
        } else {
            throw new EventParticipantException("The event participant fetched to delete doesn't exist");
        }
    }

    @Override
    public List<EventParticipantResponse> findEventParticipantsByStatus(Integer offset, Integer pageSize, String statusEventParticipant) {
        Page<EventParticipant> list = eventParticipantRepository.findEventParticipantByStatus(knowStatus(statusEventParticipant), PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventParticipantOutputMapper::toEventParticipantResponse).collect(Collectors.toList());
        } else {
            throw new EventParticipantException("The list of event participant is null");
        }
    }

    @Override
    public CustomerResponse findCustomerEventParticipant(Long id) {
        Customer customer = eventParticipantRepository.findCustomer(id).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomerResponse(customer);
        } else {
            throw new EventParticipantException("The event participant's customer doesn't exist");
        }
    }

    @Override
    public CreditCardResponse findCardEventParticipant(Long id) {
        CreditCard card = eventParticipantRepository.findCard(id).orElse(null);
        if (card != null) {
            return creditCardOutPutMapper.toCreditCardResponse(card);
        } else {
            throw new EventParticipantException("The event participant's card doesn't exist");
        }
    }
}
