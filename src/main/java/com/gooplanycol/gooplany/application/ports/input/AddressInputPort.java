package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.AddressRequest;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;

import java.util.List;

public interface AddressInputPort {

    AddressResponse save(AddressRequest address);

    AddressResponse edit(AddressRequest address, Long id);

    boolean remove(Long id);

    AddressResponse findById(Long id);

    List<AddressResponse> findAll(Integer offset, Integer pageSize);

    List<AddressResponse> findAddressesByPostalCode(Integer offset, Integer pageSize,String postalCode);

    List<AddressResponse> findAddressesByCountry(Integer offset, Integer pageSize,String country);

}
