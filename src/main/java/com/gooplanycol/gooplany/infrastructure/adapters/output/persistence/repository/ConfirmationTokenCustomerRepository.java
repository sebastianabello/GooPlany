package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenCustomerRepository extends JpaRepository<ConfirmationTokenCustomerEntity,Long> {

    Optional<ConfirmationTokenCustomerEntity> findByToken(String token);
}
