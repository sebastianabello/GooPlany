package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.utils.TypeCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Query("SELECT c FROM CreditCard c WHERE c.type =:typeCard")
    Page<CreditCard> findCreditCardsByTypeCard(Pageable pageable, @Param("typeCard") TypeCard typeCard);

    Optional<CreditCard> findCreditCardByNumber(String number);
}