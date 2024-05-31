package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCardEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipantEntity;
import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventParticipantRepository extends JpaRepository<EventParticipantEntity, Long> {
    @Query("SELECT p.customer FROM EventParticipantEntity p WHERE p.id=:id")
    Optional<CustomerEntity> findCustomer(@Param("id") Long id);

    @Query("SELECT p.creditCard FROM EventParticipantEntity p WHERE p.id=:id")
    Optional<CreditCardEntity> findCard(@Param("id") Long id);

    @Query("SELECT p FROM EventParticipantEntity p WHERE p.statusRegistration =:status")
    Page<EventParticipantEntity> findPaymentsByStatus(@Param("status") StatusEventParticipant statusEventParticipant, Pageable pageable);
}
