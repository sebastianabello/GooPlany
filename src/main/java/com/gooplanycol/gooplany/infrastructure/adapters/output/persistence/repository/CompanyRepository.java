package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT c.history FROM CompanyEntity c WHERE c.id= :customerId")
    Optional<HistoryEntity> findCompanyHistory(@Param("customerId") Long id);

    @Query("SELECT c.address FROM CompanyEntity c WHERE c.id= :customerId")
    Page<AddressEntity> findCompanyByAddress(@Param("customerId") Long id, Pageable pageable);
    @Query("SELECT c FROM CompanyEntity c WHERE c.email= :email")
    Optional<CompanyEntity> findCompanyByEmail(@Param("email") String email);

    Optional<CompanyEntity> findCompanyByName(String username);
}
