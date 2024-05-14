package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressOutputPort {
    Optional<Address> findById(Long id);

    List<Address> findAll();

    Address save(Address address);

    void deleteById(Long id);
}
