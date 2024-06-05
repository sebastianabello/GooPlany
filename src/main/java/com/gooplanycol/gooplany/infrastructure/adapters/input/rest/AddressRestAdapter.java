package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.AddressInputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
import com.gooplanycol.gooplany.domain.model.request.AddressRequest;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
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

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AddressRequest addressRequestDTO) {
        try {
            AddressResponse addressResponseDTO = addressInputPort.save(addressRequestDTO);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.CREATED);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody AddressRequest addressRequestDTO, @PathVariable("id") Long id) {
        try {
            AddressResponse addressResponseDTO = addressInputPort.edit(addressRequestDTO, id);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.CREATED);
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
            AddressResponse addressResponseDTO = addressInputPort.findById(id);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<AddressResponse> list = addressInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> list = addressInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/pc/{postalCode}/{offset}/{pageSize}")
    public ResponseEntity<?> findByPostalCode(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable("postalCode") String postalCode) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByPostalCode(offset, pageSize, postalCode);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find/pc/{postalCode}")
    public ResponseEntity<?> findByPostalCodeByDefault(@PathVariable("postalCode") String postalCode) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByPostalCode(0, 10, postalCode);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find/cnt/{country}/{offset}/{pageSize}")
    public ResponseEntity<?> findByCountry(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable("country") String country) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByCountry(offset, pageSize, country);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/cnt/{country}")
    public ResponseEntity<?> findByCountryByDefault(@PathVariable("country") String country) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByCountry(0, 10, country);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}
