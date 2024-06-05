package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.AddressInputPort;
import com.gooplanycol.gooplany.application.ports.output.AddressOutputPort;
import com.gooplanycol.gooplany.domain.model.request.AddressRequest;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressInputPort {

    private final AddressOutputPort addressOutputPort;

    @Override
    public AddressResponse save(AddressRequest addressRequest) {
        return addressOutputPort.save(addressRequest);
    }

    @Override
    public AddressResponse edit(AddressRequest addressRequest, Long id) {
        return addressOutputPort.edit(addressRequest, id);
    }

    @Override
    public boolean remove(Long id) {
        return addressOutputPort.remove(id);
    }

    @Override
    public AddressResponse findById(Long id) {
        return addressOutputPort.findById(id);
    }

    @Override
    public List<AddressResponse> findAll(Integer offset, Integer pageSize) {
        return addressOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<AddressResponse> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        return addressOutputPort.findAddressesByPostalCode(offset, pageSize, postalCode);
    }

    @Override
    public List<AddressResponse> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        return addressOutputPort.findAddressesByCountry(offset, pageSize, country);
    }
}
