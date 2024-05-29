package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.StatusEventPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPostRepository extends JpaRepository<EventPostEntity, Long> {

    @Query("SELECT e FROM EventPostEntity e WHERE e.eventCategory =:eventCategory")
    Page<EventPostEntity> findEventPostByEventCategory(Pageable pageable, @Param("eventCategory") EventCategory eventCategory);

    @Query("SELECT e FROM EventPostEntity e WHERE e.company =:companyName")
    List<EventPostEntity> findEventPostByCompanyName(@Param("companyName") String companyName);

    @Query("SELECT e FROM EventPostEntity e WHERE e.company.id =:companyId")
    List<EventPostEntity> findEventPostByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT e FROM EventPostEntity e WHERE e.statusEventPost =:statusEventPost")
    Page<EventPostEntity> findEventPostByStatus(@Param("eventStatus") String statusEventPost, Pageable pageable);

}
