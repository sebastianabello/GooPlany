package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c.history FROM Company c WHERE c.id= :companyId")
    Optional<History> findCompanyHistory(@Param("companyId") Long id);

    @Query("SELECT c.address FROM Company c WHERE c.id= :companyId")
    Page<Address> findCompanyAddress(@Param("companyId") Long id, Pageable pageable);

    Optional<Company> findCompanyByEmail(String email);

    Optional<Company> findCompanyByUsername(String username);

}
