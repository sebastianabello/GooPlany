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
        if (address == null) {
            throw new AddressException("The address to save is null");
        }
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
        if (addressOutputPort.findById(id).isPresent()) {
            addressOutputPort.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Address findById(Long id) {
        return addressOutputPort.findById(id)
                .orElseThrow(() -> new AddressException("The address with id " + id + " doesn't exist"));
    }

    @Override
    public List<Address> findAll(Integer offset, Integer pageSize) {
        if (offset == null || pageSize == null) {
            throw new AddressException("The offset and pageSize must be provided");
        } else if (offset < 0 || pageSize < 0) {
            throw new AddressException("The offset and pageSize must be greater than 0");
        }
        return addressOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        if (offset == null || pageSize == null || postalCode == null) {
            throw new AddressException("The offset, pageSize and postalCode must be provided");
        } else if (offset < 0 || pageSize < 0) {
            throw new AddressException("The offset and pageSize must be greater than 0");
        } else if (postalCode.isBlank()) {
            throw new AddressException("The postalCode must not be blank");
        }
        return addressOutputPort.findAddressesByPostalCode(offset, pageSize, postalCode);
    }

    @Override
    public List<Address> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        if (offset == null || pageSize == null || country == null) {
            throw new AddressException("The offset, pageSize and country must be provided");
        } else if (offset < 0 || pageSize < 0) {
            throw new AddressException("The offset and pageSize must be greater than 0");
        } else if (country.isBlank()) {
            throw new AddressException("The country must not be blank");
        }
        return addressOutputPort.findAddressesByCountry(offset, pageSize, country);
    }
}
