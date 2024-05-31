package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPostRepository extends JpaRepository<EventPostEntity, Long> {
    @Query("SELECT e FROM EventPostEntity e WHERE e.title=:title ")
    Page<EventPostEntity> findEventPostByTitle(@Param("title") String title, Pageable pageable);
}
