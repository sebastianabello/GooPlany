package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.domain.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.domain.model.request.CompanyRequest;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequest;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationRestAdapter {

    private final CustomerInputPort customerInputPort;
    private final RegistrationInputPort registrationInputPort;

    @PostMapping("/saveCustomer")
    public ResponseEntity<?> save(@RequestBody CustomerRequest customerRequest) throws IllegalAccessException {
        AuthenticationResponse authenticationResponseDTO = registrationInputPort.saveCustomer(customerRequest);
        if (authenticationResponseDTO != null) {
            return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveCompany")
    public ResponseEntity<?> save(@RequestBody CompanyRequest companyRequest) throws IllegalAccessException {
        AuthenticationResponse authenticationResponseDTO = registrationInputPort.saveCompany(companyRequest);
        if (authenticationResponseDTO != null) {
            return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequestDTO) {
        AuthenticationResponse responseDTO = customerInputPort.authenticate(authenticationRequestDTO);
        if (responseDTO != null) {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationInputPort.confirmToken(token), HttpStatus.OK);
    }
}
