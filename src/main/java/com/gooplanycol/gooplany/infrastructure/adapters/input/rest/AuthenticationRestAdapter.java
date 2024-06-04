package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CompanyRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CustomerRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.RegistrationRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CustomerRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationRestAdapter {

    private final CustomerInputPort customerInputPort;
    private final CustomerRestMapper customerRestMapper;
    private final CompanyInputPort companyInputPort;
    private final CompanyRestMapper companyRestMapper;

    private final RegistrationInputPort registrationInputPort;
    private final RegistrationRestMapper registrationRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CustomerRequest requestDTO) throws IllegalAccessException {
        AuthenticationResponse authenticationResponse = registrationRestMapper.toAuthenticationResponse(registrationInputPort.saveCustomer(customerRestMapper.toCustomer(requestDTO)));
        if (authenticationResponse != null) {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveCompany")
    public ResponseEntity<?> save(@RequestBody CompanyRequest requestDTO) throws IllegalAccessException {
        AuthenticationResponse authenticationResponse = registrationRestMapper.toAuthenticationResponse(registrationInputPort.saveCompany(companyRestMapper.toCompany(requestDTO)));
        if (authenticationResponse != null) {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse responseCustomer = customerRestMapper.toAuthentication(customerInputPort.authenticate(customerRestMapper.toAuthenticationCustomer(authenticationRequest)));
        AuthenticationResponse responseCompany = companyRestMapper.toAuthentication(companyInputPort.authenticate(companyRestMapper.toAuthenticationCompany(authenticationRequest)));
        if (responseCustomer != null) {
            return new ResponseEntity<>(responseCustomer, HttpStatus.OK);
        } else if (responseCompany != null) {
            return new ResponseEntity<>(responseCompany, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationInputPort.confirmToken(token), HttpStatus.OK);
    }

}
