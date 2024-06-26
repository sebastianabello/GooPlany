package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.postalCode=:postalCode")
    Page<Address> findAddressesByPostalCode(Pageable pageable, @Param("postalCode") String postalCode);

    @Query("SELECT a FROM Address a WHERE a.country=:country")
    Page<Address> findAddressesByCountry(Pageable pageable, @Param("country") String country);
}
