package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.domain.exception.CustomerException;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(
            summary = "Elimina un cliente en el sistema",
            description = "Este endpoint permite eliminar un cliente en el sistema utilizando su ID. Devuelve un estado HTTP 200 si el cliente se eliminó correctamente.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El cliente se ha eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            customerInputPort.removeCustomer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/edit")
    @Operation(
            summary = "Edita los datos de un cliente en el sistema",
            description = "Este endpoint permite editar los datos de un cliente en el sistema utilizando su ID. Se requiere un cuerpo de solicitud con los datos del cliente a editar. Devuelve un objeto CustomerResponse con los detalles del cliente editado.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El cliente se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody CustomerRequestEdit customerRequestEditDTO, @PathVariable("id") Long id) {
        try {
            CustomerResponse responseDTO = customerInputPort.editData(customerRequestEditDTO, id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("find/{id}")
    @Operation(
            summary = "Encuentra un cliente en el sistema por su ID",
            description = "Este endpoint permite buscar un cliente en el sistema utilizando su ID. Devuelve un objeto CustomerResponse con los detalles del cliente encontrado.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El cliente se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CustomerResponse responseDTO = customerInputPort.findById(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra todos los clientes en el sistema con paginación",
            description = "Este endpoint permite buscar todos los clientes en el sistema con paginación. Se requiere el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos CustomerResponse con los detalles de los clientes encontrados.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los clientes se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Clientes no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CustomerResponse> list = customerInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra todos los clientes en el sistema con paginación por defecto",
            description = "Este endpoint permite buscar todos los clientes en el sistema con una paginación por defecto (10 elementos desde el inicio). Devuelve una lista de objetos CustomerResponse con los detalles de los clientes encontrados.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los clientes se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Clientes no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<CustomerResponse> list = customerInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/history")
    @Operation(
            summary = "Encuentra el historial de un cliente en el sistema por su ID",
            description = "Este endpoint permite buscar el historial de un cliente en el sistema utilizando su ID. Devuelve un objeto HistoryResponse con los detalles del historial del cliente.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El historial del cliente se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HistoryResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Historial del cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findHistory(@PathVariable("id") Long id) {
        try {
            HistoryResponse historyResponseDTO = customerInputPort.findHistory(id);
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra las direcciones de un cliente en el sistema con paginación",
            description = "Este endpoint permite buscar las direcciones de un cliente en el sistema utilizando su ID con paginación. Se requiere el ID del cliente, el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Las direcciones se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddressResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Direcciones no encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAddress(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> addressResponseDTO = customerInputPort.findAddress(id, offset, pageSize);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/address")
    @Operation(
            summary = "Encuentra las direcciones de un cliente en el sistema con paginación por defecto",
            description = "Este endpoint permite buscar las direcciones de un cliente en el sistema utilizando su ID con una paginación por defecto (10 elementos desde el inicio). Se requiere el ID del cliente en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Las direcciones se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddressResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Direcciones no encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAddressByDefault(@PathVariable("id") Long id) {
        try {
            List<AddressResponse> addressResponseDTO = customerInputPort.findAddress(id, 0, 10);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/cards/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra las tarjetas de crédito de un cliente en el sistema con paginación",
            description = "Este endpoint permite buscar las tarjetas de crédito de un cliente en el sistema utilizando su ID con paginación. Se requiere el ID del cliente, el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito encontradas.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Las tarjetas de crédito se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Tarjetas de crédito no encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findCards(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CreditCardResponse> list = customerInputPort.findCards(id, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/cards")
    @Operation(
            summary = "Encuentra las tarjetas de crédito de un cliente en el sistema con paginación por defecto",
            description = "Este endpoint permite buscar las tarjetas de crédito de un cliente en el sistema utilizando su ID con una paginación por defecto (10 elementos desde el inicio). Se requiere el ID del cliente en la ruta de la URL. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito encontradas.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Las tarjetas de crédito se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Tarjetas de crédito no encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findCardsByDefault(@PathVariable("id") Long id) {
        try {
            List<CreditCardResponse> list = customerInputPort.findCards(id, 0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/email/{email}")
    @Operation(
            summary = "Encuentra un cliente en el sistema por su correo electrónico",
            description = "Este endpoint permite buscar un cliente en el sistema utilizando su correo electrónico. Devuelve un objeto CustomerResponse con los detalles del cliente encontrado.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El cliente se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        try {
            CustomerResponse responseDTO = customerInputPort.findByEmail(email);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/change/pwd/{pwd}")
    @Operation(
            summary = "Cambia la contraseña de un cliente en el sistema",
            description = "Este endpoint permite cambiar la contraseña de un cliente en el sistema utilizando su ID. Se requiere el ID del cliente y la nueva contraseña en la ruta de la URL. Devuelve un objeto CustomerResponse con los detalles del cliente cuya contraseña ha sido cambiada.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La contraseña del cliente se ha cambiado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> changePwd(@PathVariable("id") Long id, @PathVariable("pwd") String pwd) {
        try {
            CustomerResponse customerResponseDTO = customerInputPort.changePwd(pwd, id);
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/add/address")
    @Operation(
            summary = "Agrega una dirección a un cliente en el sistema",
            description = "Este endpoint permite agregar una dirección a un cliente en el sistema utilizando su ID. Se requiere el ID del cliente en la ruta de la URL y un cuerpo de solicitud con los detalles de la dirección a agregar. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones del cliente, incluyendo la nueva dirección agregada.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La dirección se ha agregado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddressResponse.class)
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
    public ResponseEntity<?> addAddress(@RequestBody AddressResponse addressResponseDTO, @PathVariable("id") Long id) {
        try {
            List<AddressResponse> list = customerInputPort.addAddress(addressResponseDTO, id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}/remove/address/{addressId}")
    @Operation(
            summary = "Elimina una dirección de un cliente en el sistema",
            description = "Este endpoint permite eliminar una dirección de un cliente en el sistema utilizando el ID del cliente y el ID de la dirección. Se requiere el ID del cliente y el ID de la dirección en la ruta de la URL. Devuelve un estado HTTP 200 si la dirección se eliminó correctamente.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La dirección se ha eliminado correctamente",
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
    public ResponseEntity<?> removeAddress(@PathVariable("customerId") Long customerId, @PathVariable("addressId") Long addressId) {
        try {
            customerInputPort.removeAddress(customerId, addressId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/add/card")
    @Operation(
            summary = "Agrega una tarjeta de crédito a un cliente en el sistema",
            description = "Este endpoint permite agregar una tarjeta de crédito a un cliente en el sistema utilizando su ID. Se requiere el ID del cliente en la ruta de la URL y un cuerpo de solicitud con los detalles de la tarjeta de crédito a agregar. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito del cliente, incluyendo la nueva tarjeta agregada.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La tarjeta de crédito se ha agregado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
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
    public ResponseEntity<?> addCreditCard(@RequestBody CreditCardResponse creditCardResponseDTO, @PathVariable("id") Long id) {
        try {
            List<CreditCardResponse> list = customerInputPort.addCreditCard(creditCardResponseDTO, id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}/remove/card/{cardId}")
    @Operation(
            summary = "Elimina una tarjeta de crédito de un cliente en el sistema",
            description = "Este endpoint permite eliminar una tarjeta de crédito de un cliente en el sistema utilizando el ID del cliente y el ID de la tarjeta. Se requiere el ID del cliente y el ID de la tarjeta en la ruta de la URL. Devuelve un estado HTTP 200 si la tarjeta de crédito se eliminó correctamente.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La tarjeta de crédito se ha eliminado correctamente",
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
    public ResponseEntity<?> removeCard(@PathVariable("customerId") Long customerId, @PathVariable("cardId") Long cardId) {
        try {
            customerInputPort.removeCreditCard(cardId, customerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/by/tk/{token}")
    @Operation(
            summary = "Encuentra un cliente en el sistema por su token",
            description = "Este endpoint permite buscar un cliente en el sistema utilizando su token. Se requiere el token del cliente en la ruta de la URL. Devuelve un objeto CustomerResponse con los detalles del cliente encontrado.",
            tags = {"Customer"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El cliente se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findCustomerByToken(@PathVariable("token") String token) {
        try {
            CustomerResponse responseDTO = customerInputPort.getCustomerByToken(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (CustomerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
