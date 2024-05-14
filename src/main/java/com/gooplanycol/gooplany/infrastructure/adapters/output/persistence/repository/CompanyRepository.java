package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

}
