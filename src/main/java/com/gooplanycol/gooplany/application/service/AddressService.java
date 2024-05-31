package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.AddressInputPort;
import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressInputPort {

    private final AddressOutputPort addressOutputPort;

    @Override
    public Address save(Address address) {
        return addressOutputPort.save(address);
    }

    @Override
    public Address edit(Address address, Long id) {
        return addressOutputPort.edit(address, id);
    }

    @Override
    public boolean remove(Long id) {
        return addressOutputPort.remove(id);
    }

    @Override
    public Address findById(Long id) {
        return addressOutputPort.findById(id);
    }

    @Override
    public List<Address> findAll(Integer offset, Integer pageSize) {
        return addressOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        return addressOutputPort.findAddressesByPostalCode(offset, pageSize, postalCode);
    }

    @Override
    public List<Address> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        return addressOutputPort.findAddressesByCountry(offset, pageSize, country);
    }
}
