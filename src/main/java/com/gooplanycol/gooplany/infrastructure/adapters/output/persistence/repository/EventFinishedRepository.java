package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipantEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
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
public interface EventFinishedRepository extends JpaRepository<EventFinishedEntity, Long> {

    @Query("SELECT e.eventParticipants FROM EventFinishedEntity e WHERE e.id=:eventFinishedId")
    Page<EventParticipantEntity> findEventFinishedEventParticipant(@Param("eventFinishedId") Long id, Pageable pageable);

    @Query("SELECT e FROM EventFinishedEntity e WHERE e.createAt=:date")
    List<EventFinishedEntity> findEventFinishedByCreateAt(@Param("date") LocalDateTime date);

    @Query("SELECT e.eventPost FROM EventFinishedEntity e WHERE e.id=:id")
    Optional<EventPostEntity> findEventPostEventFinished(@Param("id") Long id);

}