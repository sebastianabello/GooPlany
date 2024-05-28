package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT c.history FROM CompanyEntity c WHERE c.id= :customerId")
    Optional<HistoryEntity> findCompanyHistory(@Param("customerId") Long id);

    @Query("SELECT c.address FROM CompanyEntity c WHERE c.id= :customerId")
    Page<AddressEntity> findCustomerAddress(@Param("customerId") Long id, Pageable pageable);

    Optional<CompanyEntity> findCustomerByEmail(String email);

    Optional<CompanyEntity> findCustomerByUsername(String username);
}
