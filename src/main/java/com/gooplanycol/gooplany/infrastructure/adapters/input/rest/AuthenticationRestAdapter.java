package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CompanyRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CustomerRestMapper;
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

    @PostMapping("/customer/save")
    public ResponseEntity<?> save(@RequestBody CustomerRequest customerRequest) throws IllegalAccessException {
        AuthenticationResponse authenticationResponse = customerRestMapper.toAuthenticationResponse(registrationInputPort.save(customerRestMapper.toCustomer(customerRequest)));
        if (authenticationResponse != null) {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse responseCustomer = customerRestMapper.toAuthenticationResponse(customerInputPort.authenticate(customerRestMapper.toCustomer(authenticationRequest)));
        AuthenticationResponse responseCompany = companyRestMapper.toAuthenticationResponse(companyInputPort.authenticate(companyRestMapper.toCompany(authenticationRequest)));

        if (responseCustomer != null) {
            return new ResponseEntity<>(responseCustomer, HttpStatus.OK);
        } else if (responseCompany != null) {
            return new ResponseEntity<>(responseCompany, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/company/save")
    public ResponseEntity<?> save(@RequestBody CompanyRequest companyRequest) throws IllegalAccessException {
        AuthenticationResponse authenticationResponse = companyRestMapper.toAuthenticationResponse(registrationInputPort.save(companyRestMapper.toCompany(companyRequest)));
        if (authenticationResponse != null) {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationInputPort.confirmToken(token), HttpStatus.OK);
    }

}
