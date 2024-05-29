package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressOutputAdapter implements AddressOutputPort {

    private final AddressRepository addressRepository;
    private final AddressOutputMapper mapper;

    @Override
    public Address save(Address address) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Address> findAll(Integer offset, Integer pageSize) {
        return List.of();
    }

    @Override
    public List<Address> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        return List.of();
    }

    @Override
    public List<Address> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        return List.of();
    }
}
