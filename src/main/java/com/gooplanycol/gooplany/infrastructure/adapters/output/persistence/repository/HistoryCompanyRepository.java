package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;


import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryCompanyRepository extends JpaRepository<HistoryCompanyEntity, Long> {
    @Query("SELECT h.eventPosts FROM HistoryCompanyEntity h WHERE h.id= :historyId")
    Page<EventFinishedEntity> findHistoryEventFinished(@Param("historyId") Long id, Pageable pageable);

}
