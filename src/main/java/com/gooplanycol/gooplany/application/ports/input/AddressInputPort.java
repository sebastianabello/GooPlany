package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Address;

import java.util.List;

public interface AddressInputPort {

    Address save(Address address);

    Address edit(Address address, Long id);

    boolean remove(Long id);

    Address findById(Long id);

    List<Address> findAll(Integer offset, Integer pageSize);

    List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize,String postalCode);

    List<Address> findAddressesByCountry(Integer offset, Integer pageSize,String country);

}
