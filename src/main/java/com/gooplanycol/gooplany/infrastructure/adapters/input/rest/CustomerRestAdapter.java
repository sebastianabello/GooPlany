package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.domain.exception.CustomerException;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerRestAdapter {

    private final CustomerInputPort customerInputPort;

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            customerInputPort.removeCustomer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody CustomerRequestEdit customerRequestEditDTO, @PathVariable("id") Long id) {
        try {
            CustomerResponse responseDTO = customerInputPort.editData(customerRequestEditDTO, id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CustomerResponse responseDTO = customerInputPort.findById(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CustomerResponse> list = customerInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<CustomerResponse> list = customerInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/history")
    public ResponseEntity<?> findHistory(@PathVariable("id") Long id) {
        try {
            HistoryResponse historyResponseDTO = customerInputPort.findHistory(id);
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address/{offset}/{pageSize}")
    public ResponseEntity<?> findAddress(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> addressResponseDTO = customerInputPort.findAddress(id, offset, pageSize);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address")
    public ResponseEntity<?> findAddressByDefault(@PathVariable("id") Long id) {
        try {
            List<AddressResponse> addressResponseDTO = customerInputPort.findAddress(id, 0, 10);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/cards/{offset}/{pageSize}")
    public ResponseEntity<?> findCards(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CreditCardResponse> list = customerInputPort.findCards(id, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/cards")
    public ResponseEntity<?> findCardsByDefault(@PathVariable("id") Long id) {
        try {
            List<CreditCardResponse> list = customerInputPort.findCards(id, 0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        try {
            CustomerResponse responseDTO = customerInputPort.findByEmail(email);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/change/pwd/{pwd}")
    public ResponseEntity<?> changePwd(@PathVariable("id") Long id, @PathVariable("pwd") String pwd) {
        try {
            CustomerResponse customerResponseDTO = customerInputPort.changePwd(pwd, id);
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/add/address")
    public ResponseEntity<?> addAddress(@RequestBody AddressResponse addressResponseDTO, @PathVariable("id") Long id) {
        try {
            List<AddressResponse> list = customerInputPort.addAddress(addressResponseDTO, id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}/remove/address/{addressId}")
    public ResponseEntity<?> removeAddress(@PathVariable("customerId") Long customerId, @PathVariable("addressId") Long addressId) {
        try {
            customerInputPort.removeAddress(customerId, addressId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/add/card")
    public ResponseEntity<?> addCreditCard(@RequestBody CreditCardResponse creditCardResponseDTO, @PathVariable("id") Long id) {
        try {
            List<CreditCardResponse> list = customerInputPort.addCreditCard(creditCardResponseDTO, id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}/remove/card/{cardId}")
    public ResponseEntity<?> removeCard(@PathVariable("customerId") Long customerId, @PathVariable("cardId") Long cardId) {
        try {
            customerInputPort.removeCreditCard(cardId, customerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/by/tk/{token}")
    public ResponseEntity<?> findCustomerByToken(@PathVariable("token") String token) {
        try {
            CustomerResponse responseDTO = customerInputPort.getCustomerByToken(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
