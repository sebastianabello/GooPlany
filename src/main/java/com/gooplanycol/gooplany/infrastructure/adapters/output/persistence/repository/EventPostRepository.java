package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.StatusEventPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventPostRepository extends JpaRepository<EventPostEntity, Long> {

    @Query("SELECT e FROM EventPostEntity e WHERE e.eventCategory =:eventCategory")
    Page<EventPostEntity> findEventPostEntitiesByEventCategory(Pageable pageable, @Param("eventCategory") EventCategory eventCategory);

    @Query("SELECT e FROM EventPostEntity e WHERE e.company =:companyName")
    Page<EventPostRepository> findEventPostEntitiesByCompanyName(Pageable pageable, @Param("companyName") String companyName);

    @Query("SELECT e FROM EventPostEntity e WHERE e.eventStatus =:eventStatus")
    Page<EventPostEntity> findEventPostByStatus(@Param("eventStatus") StatusEventPost eventStatus, Pageable pageable);

}
