package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenCompanyRepository extends JpaRepository<TokenCompanyEntity, Long> {

    @Query("SELECT t FROM TokenCompanyEntity t inner join CompanyEntity c on t.company.id = c.id WHERE c.id = :companyId and (t.expired=false or t.revoked=false)")
    List<TokenCompanyEntity> findAllValidTokenByCompany(@Param("companyId") Long id);

    @Query("SELECT t.company FROM TokenCompanyEntity t WHERE t.tok= :token ")
    CompanyEntity getCompanyByToken(@Param("token") String token);

    @Query("SELECT t FROM TokenCompanyEntity t WHERE t.tok= :token ")
    Optional<TokenCompanyEntity> findTokenByToken(@Param("token") String token);
}
