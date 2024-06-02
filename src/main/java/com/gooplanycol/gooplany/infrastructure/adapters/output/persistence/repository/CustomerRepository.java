package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCardEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query("SELECT c.historyCustomer FROM CustomerEntity c WHERE c.id= :customerId")
    Optional<HistoryCustomerEntity> findCustomerHistory(@Param("customerId") Long id);

    @Query("SELECT c.address FROM CustomerEntity c WHERE c.id= :customerId")
    Page<AddressEntity> findCustomerAddress(@Param("customerId") Long id, Pageable pageable);

    @Query("SELECT c.cards FROM CustomerEntity c WHERE c.id= :customerId")
    Page<CreditCardEntity> findCustomerCreditCards(@Param("customerId") Long id, Pageable pageable);

    @Query("SELECT c FROM CustomerEntity c WHERE c.email= :email")
    Optional<CustomerEntity> findCustomerByEmail(String email);

    @Query("SELECT c FROM CustomerEntity c WHERE c.username = :username")
    Optional<CustomerEntity> findCustomerByUsername(String username);
}
