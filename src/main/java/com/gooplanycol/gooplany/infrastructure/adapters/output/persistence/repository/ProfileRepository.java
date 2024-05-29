package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    @Query("SELECT p FROM ProfileEntity p WHERE p.email = :email")
    Optional<ProfileEntity> findProfileByEmail(@Param("email") String email);

    @Query("SELECT p FROM ProfileEntity p WHERE p.username = :username")
    Optional<ProfileEntity> findProfileByUsername(@Param("username") String username);



}
