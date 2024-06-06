package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventStoke;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventStokeRepository extends JpaRepository<EventStoke, Long> {
    @Query("SELECT p FROM EventStoke p WHERE p.enableEvent =true")
    Page<EventStoke> findEventStokesByEnableEventPost(Pageable pageable);

    @Query("SELECT p FROM EventStoke p WHERE p.title=:title")
    Optional<EventStoke> findEventStockByTitle(@Param("title") String title);

    boolean existsByTitle(String name);

}
