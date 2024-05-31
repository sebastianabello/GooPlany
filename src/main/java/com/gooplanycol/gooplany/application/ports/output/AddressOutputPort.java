package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressOutputPort {

    Address save(Address address);

    Address edit(Address address, Long id);

    boolean remove(Long id);

    Optional<Address> findById(Long id);

    List<Address> findAll(Integer offset, Integer pageSize);

    List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize,String postalCode);

    List<Address> findAddressesByCountry(Integer offset, Integer pageSize,String country);
}
