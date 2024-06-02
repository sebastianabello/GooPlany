package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.AddressInputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.AddressRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AddressRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressRestAdapter {

    private final AddressInputPort addressInputPort;
    private final AddressRestMapper addressRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AddressRequest addressRequest) {
        try {
            AddressResponse addressResponse = addressRestMapper.toAddressResponse(addressInputPort.save(addressRestMapper.toAddress(addressRequest)));
            return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody AddressRequest addressRequest, @PathVariable("id") Long id) {
        try {
            AddressResponse addressResponse = addressRestMapper.toAddressResponse(addressInputPort.edit((addressRestMapper.toAddress(addressRequest)), id));
            return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            addressInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            AddressResponse addressResponse = addressRestMapper.toAddressResponse(addressInputPort.findById(id));
            return new ResponseEntity<>(addressResponse, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<AddressResponse> list = addressRestMapper.toAddressResponseList(addressInputPort.findAll(0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> list = addressRestMapper.toAddressResponseList(addressInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/pc/{postalCode}/{offset}/{pageSize}")
    public ResponseEntity<?> findByPostalCode(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable("postalCode") String postalCode) {
        try {
            List<AddressResponse> list = addressRestMapper.toAddressResponseList(addressInputPort.findAddressesByPostalCode(offset, pageSize, postalCode));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/pc/{postalCode}")
    public ResponseEntity<?> findByPostalCodeByDefault(@PathVariable("postalCode") String postalCode) {
        try {
            List<AddressResponse> list = addressRestMapper.toAddressResponseList(addressInputPort.findAddressesByPostalCode(0, 10, postalCode));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/cnt/{country}/{offset}/{pageSize}")
    public ResponseEntity<?> findByCountry(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable("country") String country) {
        try {
            List<AddressResponse> list = addressRestMapper.toAddressResponseList(addressInputPort.findAddressesByCountry(offset, pageSize, country));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/cnt/{country}")
    public ResponseEntity<?> findByCountryByDefault(@PathVariable("country") String country) {
        try {
            List<AddressResponse> list = addressRestMapper.toAddressResponseList(addressInputPort.findAddressesByCountry(0, 10, country));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}
