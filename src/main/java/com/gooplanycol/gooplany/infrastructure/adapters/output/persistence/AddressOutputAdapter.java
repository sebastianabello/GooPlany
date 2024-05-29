package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddressOutputAdapter implements AddressOutputPort {

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressOutputMapper;

    @Override
    public Address save(Address address) {
        if (address != null) {
            address.getStreet();
            address.getCountry();
            address.getPostalCode();
            return addressOutputMapper.toAddress(addressRepository.save(addressOutputMapper.toAddressEntity(address)));
        } else {
            throw new AddressException("The address to save is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        } else {
            throw new AddressException("The address to delete doesn't exist");
        }
    }

    @Override
    public Optional<Address> findById(Long id) {
        Address address = addressOutputMapper.toAddress(addressRepository.findById(id).orElse(null));
        if (address != null) {
            return Optional.of(address);
        } else {
            throw new AddressException("The address fetched by id doesn't exist");
        }
    }

    @Override
    public List<Address> findAll(Integer offset, Integer pageSize) {
        Page<Address> list = addressOutputMapper.toAddressPage(addressRepository.findAll(PageRequest.of(offset, pageSize)));
        if (list != null && !list.isEmpty()) {
            return new ArrayList<>(list.getContent());
        } else {
            throw new AddressException("The list of addresses fetched is null");
        }
    }

    @Override
    public List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        Page<Address> list = addressOutputMapper.toAddressPage(addressRepository.findAddressesByPostalCode(PageRequest.of(offset, pageSize), postalCode));
        if (list != null && !list.isEmpty()) {
            return new ArrayList<>(list.getContent());
        } else {
            throw new AddressException("The list of addresses fetched by postal code is null");
        }
    }

    @Override
    public List<Address> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        Page<Address> list = addressOutputMapper.toAddressPage(addressRepository.findAddressesByCountry(PageRequest.of(offset, pageSize), country));
        if (list != null) {
            return list.stream().collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched by country code is null");
        }
    }
}
