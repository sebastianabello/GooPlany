package com.gooplanycol.gooplany.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EventPostRepositoryTest {

    @Autowired
    private EventPostRepository eventPostRepository;

    @BeforeEach
    void setUp() {
        EventPost eventPost = new EventPost(null, "Evento 1", "des. Evento 1", EventCategory.THEATER, TypeOfAudience.ADULTS, TypeOfPlace.THEATER, false, 10000, 100, LocalDateTime.of(2024, 6, 5, 10, 0), LocalDateTime.of(2024, 6, 6, 11, 0), LocalDateTime.of(2024, 6, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost2 = new EventPost(null, "Evento 2", "des. Evento 2", EventCategory.MUSIC, TypeOfAudience.TEENAGERS, TypeOfPlace.PARK, true, 20000, 200, LocalDateTime.of(2024, 7, 5, 10, 0), LocalDateTime.of(2024, 7, 6, 11, 0), LocalDateTime.of(2024, 7, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost3 = new EventPost(null, "Evento 3", "des. Evento 3", EventCategory.SPORTS, TypeOfAudience.ADULTS, TypeOfPlace.STADIUM, false, 30000, 300, LocalDateTime.of(2024, 8, 5, 10, 0), LocalDateTime.of(2024, 8, 6, 11, 0), LocalDateTime.of(2024, 8, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost4 = new EventPost(null, "Evento 4", "des. Evento 4", EventCategory.EXHIBITION, TypeOfAudience.ELDERLY, TypeOfPlace.MUSEUM, true, 40000, 400, LocalDateTime.of(2024, 9, 5, 10, 0), LocalDateTime.of(2024, 9, 6, 11, 0), LocalDateTime.of(2024, 9, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost5 = new EventPost(null, "Evento 5", "des. Evento 5", EventCategory.CONFERENCE, TypeOfAudience.PROFESSIONALS, TypeOfPlace.UNIVERSITY, false, 50000, 500, LocalDateTime.of(2024, 10, 5, 10, 0), LocalDateTime.of(2024, 10, 6, 11, 0), LocalDateTime.of(2024, 10, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost6 = new EventPost(null, "Evento 6", "des. Evento 6", EventCategory.WORKSHOP, TypeOfAudience.STUDENTS, TypeOfPlace.SCHOOL, true, 60000, 600, LocalDateTime.of(2024, 11, 5, 10, 0), LocalDateTime.of(2024, 11, 6, 11, 0), LocalDateTime.of(2024, 11, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost7 = new EventPost(null, "Evento 7", "des. Evento 7", EventCategory.PARTY, TypeOfAudience.ADULTS, TypeOfPlace.BAR, true, 70000, 700, LocalDateTime.of(2024, 12, 5, 10, 0), LocalDateTime.of(2024, 12, 6, 11, 0), LocalDateTime.of(2024, 12, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost8 = new EventPost(null, "Evento 8", "des. Evento 8", EventCategory.FESTIVAL, TypeOfAudience.FAMILY, TypeOfPlace.PARK, false, 80000, 800, LocalDateTime.of(2025, 1, 5, 10, 0), LocalDateTime.of(2025, 1, 6, 11, 0), LocalDateTime.of(2025, 1, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost9 = new EventPost(null, "Evento 9", "des. Evento 9", EventCategory.CULTURAL, TypeOfAudience.LOCALS, TypeOfPlace.LIBRARY, true, 90000, 900, LocalDateTime.of(2025, 2, 5, 10, 0), LocalDateTime.of(2025, 2, 6, 11, 0), LocalDateTime.of(2025, 2, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost10 = new EventPost(null, "Evento 10", "des. Evento 10", EventCategory.RELIGIOUS, TypeOfAudience.FOREIGNERS, TypeOfPlace.CHURCH, false, 100000, 1000, LocalDateTime.of(2025, 3, 5, 10, 0), LocalDateTime.of(2025, 3, 6, 11, 0), LocalDateTime.of(2025, 3, 4, 07, 0), LocalDateTime.now(), null);
        EventPost eventPost11 = new EventPost(null, "Evento 11", "des. Evento 11", EventCategory.CHARITY, TypeOfAudience.TOURISTS, TypeOfPlace.BEACH, true, 110000, 1100, LocalDateTime.of(2025, 4, 5, 10, 0), LocalDateTime.of(2025, 4, 6, 11, 0), LocalDateTime.of(2025, 4, 4, 07, 0), LocalDateTime.now(), null);
        eventPostRepository.save(eventPost);
        eventPostRepository.save(eventPost2);
        eventPostRepository.save(eventPost3);
        eventPostRepository.save(eventPost4);
        eventPostRepository.save(eventPost5);
        eventPostRepository.save(eventPost6);
        eventPostRepository.save(eventPost7);
        eventPostRepository.save(eventPost8);
        eventPostRepository.save(eventPost9);
        eventPostRepository.save(eventPost10);
        eventPostRepository.save(eventPost11);
    }

    @Test
    void findProductSoldsByBarCode() {
        Page<EventPost> event1 = eventPostRepository.findEventPostByTitle("Evento 1", PageRequest.of(0, 10));
        assertTrue(!event1.isEmpty() || event1 != null);
        Page<EventPost> event2 = eventPostRepository.findEventPostByTitle("Evento 9", PageRequest.of(0, 10));
        assertFalse(event2.isEmpty() || event2 == null);
    }
}
