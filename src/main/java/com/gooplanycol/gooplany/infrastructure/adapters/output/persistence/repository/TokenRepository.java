package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("SELECT t FROM TokenEntity t inner join CustomerEntity c on t.customer.id= c.id WHERE c.id= :customerId and (t.expired=false or t.revoked=false)")
    List<TokenEntity> findAllValidTokenByCustomer(@Param("customerId") Long id);

    @Query("SELECT t FROM TokenEntity t inner join CompanyEntity c on t.company.id = c.id WHERE c.id = :companyId and (t.expired=false or t.revoked=false)")
    List<TokenEntity> findAllValidTokenByCompany(@Param("companyId") Long id);

    @Query("SELECT t.company FROM TokenEntity t WHERE t.tok= :token ")
    CustomerEntity getCustomerByToken(@Param("token") String token);

    @Query("SELECT t.company FROM TokenEntity t WHERE t.tok= :token ")
    CompanyEntity getCompanyByToken(@Param("token") String token);

    Optional<TokenEntity> findTokenByToken(String token);
}
