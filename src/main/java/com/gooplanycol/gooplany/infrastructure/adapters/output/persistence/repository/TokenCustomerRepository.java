package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenCustomerRepository extends JpaRepository<TokenCustomerEntity, Long> {

    @Query("SELECT t FROM TokenCustomerEntity t inner join CustomerEntity c on t.customer.id = c.id WHERE c.id= :customerId and (t.expired=false or t.revoked=false)")
    List<TokenCustomerEntity> findAllValidTokenByCustomer(@Param("customerId") Long id);

    @Query("SELECT t.customer FROM TokenCustomerEntity t WHERE t.tok= :token ")
    CustomerEntity getCustomerByToken(@Param("token") String token);

    @Query("SELECT t FROM TokenCustomerEntity t WHERE t.tok= :token ")
    Optional<TokenCustomerEntity> findTokenByToken(@Param("token") String token);
}
