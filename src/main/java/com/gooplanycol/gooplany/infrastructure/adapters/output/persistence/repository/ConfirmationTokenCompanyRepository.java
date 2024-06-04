package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenCompanyRepository extends JpaRepository<ConfirmationTokenCompanyEntity,Long> {

    Optional<ConfirmationTokenCompanyEntity> findByToken(String token);
}
