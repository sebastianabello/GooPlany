package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.exception.CustomerException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CompanyRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequestEdit;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.*;
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
    private final CompanyRestMapper companyRestMapper;

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
            CompanyResponse responseDTO = companyRestMapper.toCompanyResponse(companyInputPort.editData(companyRestMapper.toCompany(customerRequestEditDTO), id));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CompanyResponse responseDTO = companyRestMapper.toCompanyResponse(companyInputPort.findById(id));
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CompanyResponse> list = companyRestMapper.toCompanyResponseList(companyInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<CompanyResponse> list = companyRestMapper.toCompanyResponseList(companyInputPort.findAll(0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/history")
    public ResponseEntity<?> findHistory(@PathVariable("id") Long id) {
        try {
            HistoryResponse historyResponseDTO = companyRestMapper.toHistoryResponse(companyInputPort.findHistory(id));
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address/{offset}/{pageSize}")
    public ResponseEntity<?> findAddress(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> addressResponseDTO = companyRestMapper.toAddressResponseList(companyInputPort.findAddress(id, offset, pageSize));
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address")
    public ResponseEntity<?> findAddressByDefault(@PathVariable("id") Long id) {
        try {
            List<AddressResponse> addressResponseDTO = companyRestMapper.toAddressResponseList(companyInputPort.findAddress(id, 0, 10));
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        try {
            CompanyResponse responseDTO = companyRestMapper.toCompanyResponse(companyInputPort.findByEmail(email));
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/change/pwd/{pwd}")
    public ResponseEntity<?> changePwd(@PathVariable("id") Long id, @PathVariable("pwd") String pwd) {
        try {
            CompanyResponse customerResponseDTO = companyRestMapper.toCompanyResponse(companyInputPort.changePwd(pwd, id));
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add/address")
    public ResponseEntity<?> addAddress(@RequestBody AddressResponse addressResponseDTO, @PathVariable("id") Long id) {
        try {
            List<AddressResponse> list = companyRestMapper.toAddressResponseList(companyInputPort.addAddress(companyRestMapper.toAddress(addressResponseDTO), id));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CompanyException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}/remove/address/{addressId}")
    public ResponseEntity<?> removeAddress(@PathVariable("customerId") Long customerId, @PathVariable("addressId") Long addressId) {
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
            CompanyResponse responseDTO = companyRestMapper.toCompanyResponse(companyInputPort.getCompanyByToken(token));
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
