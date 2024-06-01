package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventStokeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventStokeRepository extends JpaRepository<EventStokeEntity, Long> {
    @Query("SELECT p FROM EventStokeEntity p WHERE p.statusEventPost=:statusEventPost")
    Page<EventStokeEntity> findEventStokesByStatusEventPost(@Param("statusEventPost") String statusEventPost, Pageable pageable);

    Optional<EventStokeEntity> findProductStockByTitle(String barcode);

    boolean existsByTitle(String name);

}
