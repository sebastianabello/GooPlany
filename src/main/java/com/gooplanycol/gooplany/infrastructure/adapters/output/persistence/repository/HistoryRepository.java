package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    @Query("SELECT h.eventsFinished FROM HistoryEntity h WHERE h.id= :historyId")
    Page<EventFinishedEntity> findHistoryEventsFinished(@Param("historyId")Long id, Pageable pageable);
}
