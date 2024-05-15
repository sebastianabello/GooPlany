package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistrationEntity, Long> {

    List<EventRegistrationEntity> findAllByEventPostId(Long eventPostId);
    Optional<EventRegistrationEntity> findByProfileIdAndEventPostId(Long profileId, Long eventPostId);
}
