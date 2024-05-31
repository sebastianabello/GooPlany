package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;


import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryCustomerRepository extends JpaRepository<HistoryCustomerEntity, Long> {
    @Query("SELECT h.eventPosts FROM HistoryCompanyEntity h WHERE h.id= :historyId")
    Page<EventPostEntity> findHistoryEventPosts(@Param("historyId") Long id, Pageable pageable);

}
