package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT c.historyCompany FROM CompanyEntity c WHERE c.id= :companyId")
    Optional<HistoryCompanyEntity> findCompanyHistory(@Param("companyId") Long id);

    @Query("SELECT c.address FROM CompanyEntity c WHERE c.id= :companyId")
    Page<AddressEntity> findCompanyAddress(@Param("companyId") Long id, Pageable pageable);

    @Query("SELECT c FROM CompanyEntity c WHERE c.username = :username")
    Optional<CompanyEntity> findCompanyByUsername(@Param("username") String username);

    @Query("SELECT c FROM CompanyEntity c WHERE c.email= :email")
    Optional<CompanyEntity> findCompanyByEmail(@Param("email") String email);
}
