package com.gooplanycol.gooplany.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.*;
import com.gooplanycol.gooplany.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private EventPostRepository eventPostRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventParticipantRepository eventParticipantRepository;
    @Autowired
    private EventFinishedRepository eventFinishedRepository;

    @BeforeEach
    void setUp() {

        // Creación de customer 1 con tarjetas de credito

        CreditCard creditCard1 = new CreditCard(null, "1103839272112", TypeCard.AMERICAN_EXPRESS);
        List<CreditCard> cards1 = new ArrayList<>();
        cards1.add(creditCard1);

        Customer customer1 = new Customer();
        customer1.setUsername("sebastian1");
        customer1.setPwd("sebastian123");
        customer1.setRoles(List.of(Role.CUSTOMER));
        customer1.setEnable(true);
        customer1.setName("sebastian");
        customer1.setLastName("abello");
        customer1.setCellphone("89262293");
        customer1.setEmail("sebas@gmail.com");
        customer1.setCards(cards1);

        customer1 = customerRepository.save(customer1);

        // Creando participante de de customer 1 a evento 1

        EventParticipant eventParticipant1 = new EventParticipant(
                null,
                StatusEventParticipant.PENDING,
                LocalDateTime.now(),
                customer1,
                creditCard1
        );

        eventParticipantRepository.save(eventParticipant1);


        // Creación de customer 1 con tarjetas de credito

        CreditCard creditCard2 = new CreditCard(null, "1103839276482", TypeCard.VISA);
        List<CreditCard> cards2 = new ArrayList<>();
        cards2.add(creditCard2);


        Customer customer2 = new Customer();
        customer2.setUsername("rodrigo123");
        customer2.setHistory(new History());
        customer2.setPwd("sebastian123");
        customer2.setRoles(List.of(Role.CUSTOMER));
        customer2.setEnable(true);
        customer2.setName("rodrigo");
        customer2.setLastName("aranda");
        customer2.setCellphone("892622932");
        customer2.setEmail("rodrigo@gmail.com");
        customer2.setCards(cards2);

        customerRepository.save(customer2);

        // Creando participante de de customer 2 a evento 1

        EventParticipant eventParticipant2 = new EventParticipant(
                null,
                StatusEventParticipant.PENDING,
                LocalDateTime.now(),
                customer2,
                creditCard2
        );
        eventParticipantRepository.save(eventParticipant2);

        // cambio el estado de participación
        eventParticipant2.setStatusRegistration(StatusEventParticipant.REGISTERED);
        eventParticipantRepository.save(eventParticipant2);

        // Crear lista de participantes con estado diferente a REGISTERED
        List<EventParticipant> eventRegistrationPending = Arrays.asList(eventParticipant1);

        // Crear una lista de todos los participantes, no toma en cuenta el estado de registro
        List<EventParticipant> allParticipants = Arrays.asList(eventParticipant1, eventParticipant2);

        // Crear una lista para almacenar solo los participantes con estado REGISTERED
        List<EventParticipant> registeredParticipants = new ArrayList<>();

        // Verificar el estado de cada participante
        for (EventParticipant participant : allParticipants) {
            if (participant.getStatusRegistration() == StatusEventParticipant.REGISTERED) {
                registeredParticipants.add(participant);
            }
        }

        //Creacion de un evento nuevo en el sistema

        EventPost eventPost = new EventPost(
                null,
                "Evento 1",
                "des. Evento 1",
                EventCategory.THEATER,
                TypeOfAudience.ADULTS,
                TypeOfPlace.THEATER,
                false,
                10000,
                100,
                LocalDateTime.of(2024, 6, 5, 10, 0),
                LocalDateTime.of(2024, 6, 6, 11, 0),
                LocalDateTime.of(2024, 6, 4, 07, 0),
                LocalDateTime.now(),
                null
        );

        eventPostRepository.saveAndFlush(eventPost);

        // Cuando el evento finaliza se crea un evento finalizado, con el evento y el listado de participantes registrados


        EventFinished eventFinished = new EventFinished();
        // cambiar para comprobar
        eventFinished.setCreateAt(LocalDateTime.now());

        eventFinishedRepository.save(eventFinished);

        // Guarda el evento finalizado en el historial

        History history = History.builder()
                .eventsFinished(List.of(eventFinished))
                .dateModification(LocalDate.now())
                .build();
        history.setDateModification(LocalDate.now());

        historyRepository.save(history);
    }

    @Test
    void findHistoryRegisteredParticipants() {
        Page<EventFinished> eventsFinished = historyRepository.findHistoryEventsFinished(1L, PageRequest.of(0, 10));
        assertTrue(eventsFinished != null);
    }

    @Test
    void eventWithRegisteredParticipants() {
        Page<EventFinished> eventsFinished = historyRepository.findHistoryEventsFinished(3L, PageRequest.of(0, 10));
        // Verifica que todos los participantes en cada evento finalizado tienen el estado REGISTERED
        for (EventFinished retrievedEvent : eventsFinished.getContent()) {
            for (EventParticipant participant : retrievedEvent.getEventParticipants()) {
                assertEquals(StatusEventParticipant.REGISTERED, participant.getStatusRegistration());
            }
        }
    }

    @Test
    void eventWithRegisteredParticipantsDif() {
        Page<EventFinished> eventsFinished = historyRepository.findHistoryEventsFinished(1L, PageRequest.of(0, 10));
        // Verifica que todos los participantes en cada evento finalizado tienen el estado REGISTERED
        for (EventFinished retrievedEvent : eventsFinished.getContent()) {
            for (EventParticipant participant : retrievedEvent.getEventParticipants()) {
                if (participant.getStatusRegistration() != StatusEventParticipant.REGISTERED) {
                    fail("Se encontró un participante con un estado de registro diferente a REGISTERED");
                }
            }
        }
    }


}
