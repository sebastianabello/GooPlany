package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.history FROM Customer c WHERE c.id= :customerId")
    Optional<History> findCustomerHistory(@Param("customerId") Long id);

    @Query("SELECT c.address FROM Customer c WHERE c.id= :customerId")
    Page<Address> findCustomerAddress(@Param("customerId") Long id, Pageable pageable);

    @Query("SELECT c.cards FROM Customer c WHERE c.id= :customerId")
    Page<CreditCard> findCustomerCreditCards(@Param("customerId") Long id, Pageable pageable);

    Optional<Customer> findCustomerByEmail(String email);

    Optional<Customer> findCustomerByUsername(String username);
}
