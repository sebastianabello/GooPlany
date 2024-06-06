package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventParticipant;
import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    @Query("SELECT p.customerId FROM EventParticipant p WHERE p.id=:id")
    Optional<Customer> findCustomer(@Param("id") Long id);

    @Query("SELECT p.creditCardId FROM EventParticipant p WHERE p.id=:id")
    Optional<CreditCard> findCard(@Param("id") Long id);

    @Query("SELECT p FROM EventParticipant p WHERE p.statusRegistration =:status")
    Page<EventParticipant> findEventParticipantByStatus(@Param("status") StatusEventParticipant statusEventParticipant, Pageable pageable);
}
