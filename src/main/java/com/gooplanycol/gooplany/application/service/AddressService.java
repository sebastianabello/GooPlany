package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.AddressInputPort;
import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
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
        return addressOutputPort.findById(id)
                .map(addressFound -> {
                    addressFound.setCountry(address.getCountry());
                    addressFound.setStreet(address.getStreet());
                    addressFound.setPostalCode(address.getPostalCode());
                    return addressOutputPort.save(addressFound);
                })
                .orElseThrow(() -> new AddressException("The address to update doesn't exist or the request is null"));
    }

    @Override
    public boolean remove(Long id) {
        return addressOutputPort.remove(id);
    }

    @Override
    public Address findById(Long id) {
        return addressOutputPort.findById(id)
                .orElseThrow(() -> new AddressException("The address with id " + id + " doesn't exist"));
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
