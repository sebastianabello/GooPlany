package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventRegistrationEntity;
import com.gooplanycol.gooplany.utils.StatusRegistrationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistrationEntity, Long> {

    List<EventRegistrationEntity> findAllByEventPostId(Long eventPostId);

    Optional<EventRegistrationEntity> findByProfileIdAndEventPostId(Long profileId, Long eventPostId);

    @Query("SELECT e FROM EventRegistrationEntity e WHERE e.profileId = :profileId AND e.eventPostId = :eventPostId")
    boolean existsByProfileIdAndEventPostId(@Param("profileId") Long profileId,@Param("eventPostId") Long eventPostId);

    @Query("SELECT e FROM EventRegistrationEntity e WHERE e.statusRegistrationEvent = :statusRegistrationEvent")
    List<EventRegistrationEntity> findByStatusRegistrationEvent(@Param("statusRegistrationEvent") StatusRegistrationEvent statusRegistrationEvent);

}
