package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Address;

import java.util.List;

public interface AddressInputPort {
    Address findById(Long id);

    List<Address> findAll();

    Address save(Address address);

    Address update(Long id,Address company);

    void deleteById(Long id);
}
