package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT p FROM CustomerEntity p WHERE p.email = :email")
    Optional<CustomerEntity> findProfileByEmail(@Param("email") String email);

    @Query("SELECT p FROM CustomerEntity p WHERE p.username = :username")
    Optional<CustomerEntity> findProfileByUsername(@Param("username") String username);

}
