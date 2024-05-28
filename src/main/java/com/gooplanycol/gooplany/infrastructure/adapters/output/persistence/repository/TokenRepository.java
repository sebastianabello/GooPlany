package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;


import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ProfileEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity,Long> {

    @Query("SELECT t FROM TokenEntity t inner join ProfileEntity p on t.profile.id = p.id WHERE p.id = :profileId and (t.expired=false or t.revoked=false)")
    List<TokenEntity> findAllValidTokenByProfile(@Param("profileId")Long id);

    @Query("SELECT t.profile FROM TokenEntity t WHERE t.token= :token ")
    ProfileEntity getCustomerByToken(@Param("token")String token);

    Optional<TokenEntity> findTokenByToken(String token);
}
