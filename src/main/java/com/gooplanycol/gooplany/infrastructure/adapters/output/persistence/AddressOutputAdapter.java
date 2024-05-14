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
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id)
                .map(mapper::toAddress);
    }

    @Override
    public List<Address> findAll() {
        return mapper.toAddressList(addressRepository.findAll());
    }

    @Override
    public Address save(Address address) {
        return mapper.toAddress(addressRepository.save(mapper.toAddressEntity(address)));
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
