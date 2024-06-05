package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.model.request.CompanyRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.domain.model.response.CompanyResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CompanyRestAdapter {

    private final CompanyInputPort companyInputPort;

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            companyInputPort.removeCompany(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody CompanyRequestEdit customerRequestEditDTO, @PathVariable("id") Long id) {
        try {
            CompanyResponse responseDTO = companyInputPort.editData(customerRequestEditDTO, id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CompanyResponse responseDTO = companyInputPort.findById(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CompanyResponse> list = companyInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<CompanyResponse> list = companyInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/history")
    public ResponseEntity<?> findHistory(@PathVariable("id") Long id) {
        try {
            HistoryResponse historyResponseDTO = companyInputPort.findHistory(id);
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address/{offset}/{pageSize}")
    public ResponseEntity<?> findAddress(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> addressResponseDTO = companyInputPort.findAddress(id, offset, pageSize);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address")
    public ResponseEntity<?> findAddressByDefault(@PathVariable("id") Long id) {
        try {
            List<AddressResponse> addressResponseDTO = companyInputPort.findAddress(id, 0, 10);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        try {
            CompanyResponse responseDTO = companyInputPort.findByEmail(email);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/change/pwd/{pwd}")
    public ResponseEntity<?> changePwd(@PathVariable("id") Long id, @PathVariable("pwd") String pwd) {
        try {
            CompanyResponse customerResponseDTO = companyInputPort.changePwd(pwd, id);
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add/address")
    public ResponseEntity<?> addAddress(@RequestBody AddressResponse addressResponseDTO, @PathVariable("id") Long id) {
        try {
            List<AddressResponse> list = companyInputPort.addAddress(addressResponseDTO, id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{companyId}/remove/address/{addressId}")
    public ResponseEntity<?> removeAddress(@PathVariable("companyId") Long customerId, @PathVariable("addressId") Long addressId) {
        try {
            companyInputPort.removeAddress(customerId, addressId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/by/tk/{token}")
    public ResponseEntity<?> findCompanyByToken(@PathVariable("token") String token) {
        try {
            CompanyResponse responseDTO = companyInputPort.getCompanyByToken(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
