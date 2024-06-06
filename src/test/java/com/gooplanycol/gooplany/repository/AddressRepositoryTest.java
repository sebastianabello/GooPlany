package com.gooplanycol.gooplany.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        Address address1 = new Address(null,"Calle 19","bogota","28903");
        Address address2 = new Address(null,"Avenida caracas","bogota","28903");
        Address address3 = new Address(null,"Los rosales","cali","28903");
        Address address4 = new Address(null,"Avenida cali","barranquilla","28904");
        Address address5 = new Address(null,"Chapinero","medellin","28904");
        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);
        addressRepository.save(address4);
        addressRepository.save(address5);
    }

    @Test
    void findAddressesByPostalCode() {
        Page<Address> address = addressRepository.findAddressesByPostalCode(PageRequest.of(0,10),"28903");
        assertEquals(address.getTotalElements(),3);
    }

    @Test
    void findAddressesByCountry() {
        Page<Address> addresses = addressRepository.findAddressesByCountry(PageRequest.of(0,10),"bogota");
        assertEquals(addresses.getTotalElements(),2);
    }


}
