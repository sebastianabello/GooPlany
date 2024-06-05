package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinished;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipant;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface EventFinishedRepository extends JpaRepository<EventFinished, Long> {

    @Query("SELECT e.eventParticipants FROM EventFinished e WHERE e.id=:eventFinishedId")
    Page<EventParticipant> findEventFinishedEventParticipant(@Param("eventFinishedId") Long id, Pageable pageable);

    @Query("SELECT e FROM EventFinished e WHERE e.createAt=:date")
    List<EventFinished> findEventFinishedByCreateAt(@Param("date") LocalDateTime date);

    @Query("SELECT e.eventPost FROM EventFinished e WHERE e.id=:id")
    Optional<EventPost> findEventPostEventFinished(@Param("id") Long id);

}