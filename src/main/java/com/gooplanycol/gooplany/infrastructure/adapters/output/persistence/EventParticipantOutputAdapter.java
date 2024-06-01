package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventParticipantOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventParticipantException;
import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCardEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipantEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutPutMapper;
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
    private final CreditCardOutPutMapper creditCardOutPutMapper;


    @Override
    public EventParticipant save(EventParticipant eventParticipant) {
        if (eventParticipant != null) {
            EventParticipantEntity e = EventParticipantEntity.builder()
                    .statusRegistration(knowStatus(eventParticipant.getStatusRegistration().name()))
                    .registeredAt(LocalDateTime.now())
                    .customer(findCustomer(eventParticipant.getCustomer().getId()))
                    .creditCard(findCreditCard(eventParticipant.getCreditCard().getId()))
                    .build();
            return eventParticipantOutputMapper.toEventParticipant(eventParticipantRepository.save(e));
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

    public CustomerEntity findCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    private CreditCardEntity findCreditCard(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public EventParticipant edit(EventParticipant eventParticipant, Long id) {
        EventParticipantEntity e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null && eventParticipant != null) {
            e.setStatusRegistration(knowStatus(eventParticipant.getStatusRegistration().name()));
            e.setRegisteredAt(LocalDateTime.now());
            e.setCustomer(findCustomer(eventParticipant.getCustomer().getId()));
            e.setCreditCard(findCreditCard(eventParticipant.getCreditCard().getId()));
            e = eventParticipantRepository.save(e);
            return eventParticipantOutputMapper.toEventParticipant(e);
        } else {
            throw new EventParticipantException("The event participant fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public EventParticipant changeStatus(String status, Long id) {
        EventParticipantEntity e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null) {
            e.setStatusRegistration(knowStatus(status));
            e = eventParticipantRepository.save(e);
            return eventParticipantOutputMapper.toEventParticipant(e);
        } else {
            throw new EventParticipantException("The event participant fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public List<EventParticipant> findAll(Integer offset, Integer pageSize) {
        Page<EventParticipantEntity> list = eventParticipantRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(eventParticipantOutputMapper::toEventParticipant).collect(Collectors.toList());
    }

    @Override
    public EventParticipant findById(Long id) {
        EventParticipantEntity e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null) {
            return eventParticipantOutputMapper.toEventParticipant(e);
        } else {
            throw new EventParticipantException("The event participant fetched by id doesn't exist");
        }
    }

    @Override
    public boolean remove(Long id) {
        EventParticipantEntity e = eventParticipantRepository.findById(id).orElse(null);
        if (e != null) {
            eventParticipantRepository.delete(e);
            return true;
        } else {
            throw new EventParticipantException("The event participant fetched to delete doesn't exist");
        }
    }

    @Override
    public List<EventParticipant> findEventParticipantsByStatus(Integer offset, Integer pageSize, String statusEventParticipant) {
        Page<EventParticipantEntity> list = eventParticipantRepository.findEventParticipantByStatus(knowStatus(statusEventParticipant), PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventParticipantOutputMapper::toEventParticipant).collect(Collectors.toList());
        } else {
            throw new EventParticipantException("The list of event participant is null");
        }
    }

    @Override
    public Customer findCustomerEventParticipant(Long id) {
        CustomerEntity customer = eventParticipantRepository.findCustomer(id).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomer(customer);
        } else {
            throw new EventParticipantException("The event participant's customer doesn't exist");
        }
    }

    @Override
    public CreditCard findCardEventParticipant(Long id) {
        CreditCardEntity card = eventParticipantRepository.findCard(id).orElse(null);
        if (card != null) {
            return creditCardOutPutMapper.toCreditCard(card);
        } else {
            throw new EventParticipantException("The event participant's card doesn't exist");
        }
    }
}
