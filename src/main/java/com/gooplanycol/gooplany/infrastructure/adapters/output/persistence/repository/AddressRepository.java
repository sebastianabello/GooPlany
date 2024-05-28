package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("SELECT a FROM AddressEntity a WHERE a.postalCode=:postalCode")
    Page<AddressEntity> findAddressesByPostalCode(Pageable pageable, @Param("postalCode") String postalCode);

    @Query("SELECT a FROM AddressEntity a WHERE a.postalCode=:country")
    Page<AddressEntity> findAddressesByCountry(Pageable pageable, @Param("country") String country);
}
