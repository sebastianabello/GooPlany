package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddressOutputAdapter implements AddressOutputPort {

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressOutputMapper;

    @Override
    public Address save(Address address) {
        if (address != null) {
            AddressEntity addressEntity = new AddressEntity(
                    null,
                    address.getStreet(),
                    address.getCountry(),
                    address.getPostalCode()
            );
            return addressOutputMapper.toAddress(addressRepository.save(addressEntity));
        } else {
            throw new AddressException("The address to save is null");
        }
    }

    @Override
    public Address edit(Address address, Long id) {
        AddressEntity addressEntity = addressRepository.findById(id).orElse(null);
        if (addressEntity != null && address != null) {
            addressEntity.setCountry(address.getCountry());
            addressEntity.setStreet(address.getStreet());
            addressEntity.setPostalCode(address.getPostalCode());
            return addressOutputMapper.toAddress(addressRepository.save(addressEntity));
        } else {
            throw new AddressException("The address to update doesn't exist or the request is null");
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
    public Address findById(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id).orElse(null);
        if (addressEntity != null) {
            return addressOutputMapper.toAddress(addressEntity);
        } else {
            throw new AddressException("The address fetched by id doesn't exist");
        }
    }

    @Override
    public List<Address> findAll(Integer offset, Integer pageSize) {
        Page<AddressEntity> list = addressRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.getContent().stream().map(addressOutputMapper::toAddress).collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched is null");
        }
    }

    @Override
    public List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        Page<AddressEntity> list = addressRepository.findAddressesByPostalCode(PageRequest.of(offset, pageSize), postalCode);
        if (list != null && !list.isEmpty()) {
            return list.getContent().stream().map(addressOutputMapper::toAddress).collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched by postal code is null");
        }
    }

    @Override
    public List<Address> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        Page<AddressEntity> list = addressRepository.findAddressesByCountry(PageRequest.of(offset, pageSize), country);
        if (list != null) {
            return list.stream().map(addressOutputMapper::toAddress).collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched by country code is null");
        }
    }
}
