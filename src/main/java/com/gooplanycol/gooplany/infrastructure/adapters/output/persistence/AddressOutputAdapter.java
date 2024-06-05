package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
import com.gooplanycol.gooplany.domain.model.request.AddressRequest;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
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
    public AddressResponse save(AddressRequest addressRequest) {
        if (addressRequest != null) {
            Address address = new Address(
                    null,
                    addressRequest.street(),
                    addressRequest.country(),
                    addressRequest.postalCode()
            );
            Address addressSaved = addressRepository.save(address);
            return addressOutputMapper.toAddressResponse(addressSaved);
        } else {
            throw new AddressException("The address to save is null");
        }
    }

    @Override
    public AddressResponse edit(AddressRequest addressRequest, Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null && addressRequest != null) {
            address.setCountry(addressRequest.country());
            address.setStreet(addressRequest.street());
            address.setPostalCode(addressRequest.postalCode());
            return addressOutputMapper.toAddressResponse(addressRepository.save(address));
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
    public AddressResponse findById(Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null) {
            return addressOutputMapper.toAddressResponse(address);
        } else {
            throw new AddressException("The address fetched by id doesn't exist");
        }
    }

    @Override
    public List<AddressResponse> findAll(Integer offset, Integer pageSize) {
        Page<Address> list = addressRepository.findAll(PageRequest.of(offset, pageSize));
        if (list != null && !list.isEmpty()) {
            return list.getContent().stream().map(address -> {
                return addressOutputMapper.toAddressResponse(address);
            }).collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched is null");
        }
    }

    @Override
    public List<AddressResponse> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        Page<Address> list = addressRepository.findAddressesByPostalCode(PageRequest.of(offset, pageSize), postalCode);
        if (list != null && !list.isEmpty()) {
            return list.getContent()
                    .stream().map(address -> {
                        return addressOutputMapper.toAddressResponse(address);
                    }).collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched by postal code is null");
        }
    }

    @Override
    public List<AddressResponse> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        Page<Address> list = addressRepository.findAddressesByCountry(PageRequest.of(offset, pageSize), country);
        if (list != null) {
            return list
                    .stream().map(address -> {
                        return addressOutputMapper.toAddressResponse(address);
                    }).collect(Collectors.toList());
        } else {
            throw new AddressException("The list of addresses fetched by country code is null");
        }
    }
}
