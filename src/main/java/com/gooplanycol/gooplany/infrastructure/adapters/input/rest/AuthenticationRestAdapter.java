package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.application.ports.input.RegistrationInputPort;
import com.gooplanycol.gooplany.domain.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequest;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @PostMapping("/save")
    @Operation(
            summary = "Guarda un nuevo cliente en el sistema",
            description = "Este endpoint permite a los usuarios guardar un nuevo cliente en el sistema. Se requiere un objeto CustomerRequest en el cuerpo de la solicitud. Devuelve un objeto AuthenticationResponse con los detalles de la autenticación del cliente.",
            tags = {"Authentication"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "El cliente se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> save(@RequestBody CustomerRequest customerRequest) throws IllegalAccessException {
        AuthenticationResponse authenticationResponseDTO = registrationInputPort.saveCustomer(customerRequest);
        if (authenticationResponseDTO != null) {
            return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "Autentica un cliente en el sistema",
            description = "Este endpoint permite a los usuarios autenticarse en el sistema. Se requiere un objeto AuthenticationRequest en el cuerpo de la solicitud. Devuelve un objeto AuthenticationResponse con los detalles de la autenticación del cliente.",
            tags = {"Authentication"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El cliente se ha autenticado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequestDTO) {
        AuthenticationResponse responseDTO = customerInputPort.authenticate(authenticationRequestDTO);
        if (responseDTO != null) {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm")
    @Operation(
            summary = "Confirma el token de un cliente en el sistema",
            description = "Este endpoint permite a los usuarios confirmar su token en el sistema. Se requiere el token en la consulta de la URL. Devuelve un objeto String con el estado de la confirmación del token.",
            tags = {"Authentication"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El token del cliente se ha confirmado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationInputPort.confirmToken(token), HttpStatus.OK);
    }
}
