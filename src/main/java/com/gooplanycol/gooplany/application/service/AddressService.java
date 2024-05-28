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
    public Address findById(Long id) {
        return addressOutputPort.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Address> findAll() {
        return addressOutputPort.findAll();
    }

    @Override
    public Address save(Address address) {
        return addressOutputPort.save(address);
    }

    @Override
    public Address update(Long id, Address company) {
        return addressOutputPort.findById(id)
                .map(addressFound -> {
                    addressFound.setCountry(company.getCountry());
                    addressFound.setDepartment(company.getDepartment());
                    addressFound.setNeighborhood(company.getNeighborhood());
                    addressFound.setStreetName(company.getStreetName());
                    addressFound.setStreetNumber(company.getStreetNumber());
                    addressFound.setTypeOfStreet(company.getTypeOfStreet());
                    addressFound.setComplement(company.getComplement());
                    return addressOutputPort.save(addressFound);
                })
                .orElseThrow(AddressException::new);
    }

    @Override
    public void deleteById(Long id) {
        addressOutputPort.deleteById(id);
    }
}
