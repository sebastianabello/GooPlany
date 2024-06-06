package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CreditCardInputPort;
import com.gooplanycol.gooplany.domain.exception.CreditCardException;
import com.gooplanycol.gooplany.domain.model.request.CreditCardRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
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
@RequestMapping("/api/v1/card")
public class CreditCardRestAdapter {

    private final CreditCardInputPort creditCardInputPort;

    @PostMapping("/save")
    @Operation(
            summary = "Guarda una nueva tarjeta de crédito en el sistema",
            description = "Este endpoint permite guardar una nueva tarjeta de crédito en el sistema. Se requiere un objeto CreditCardRequest en el cuerpo de la solicitud. Devuelve un objeto CreditCardResponse con los detalles de la tarjeta de crédito guardada.",
            tags = {"Credit Card"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "La tarjeta de crédito se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Solicitud incorrecta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> save(@RequestBody CreditCardRequest request) {
        try {
            CreditCardResponse response = creditCardInputPort.save(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    @Operation(
            summary = "Edita una tarjeta de crédito existente en el sistema",
            description = "Este endpoint permite editar una tarjeta de crédito existente en el sistema. Se requiere un objeto CreditCardRequest en el cuerpo de la solicitud y el ID de la tarjeta de crédito en la ruta de la URL. Devuelve un objeto CreditCardResponse con los detalles de la tarjeta de crédito editada.",
            tags = {"Credit Card"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La tarjeta de crédito se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Solicitud incorrecta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody CreditCardRequest request, @PathVariable("id") Long id) {
        try {
            CreditCardResponse response = creditCardInputPort.edit(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Encuentra una tarjeta de crédito por su ID en el sistema",
            description = "Este endpoint permite buscar una tarjeta de crédito en el sistema utilizando su ID. Devuelve un objeto CreditCardResponse con los detalles de la tarjeta de crédito encontrada.",
            tags = {"Credit Card"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "La tarjeta de crédito se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Tarjeta de crédito no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CreditCardResponse response = creditCardInputPort.findById(id);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/number/{number}")
    @Operation(
            summary = "Encuentra una tarjeta de crédito por su número en el sistema",
            description = "Este endpoint permite buscar una tarjeta de crédito en el sistema utilizando su número. Devuelve un objeto CreditCardResponse con los detalles de la tarjeta de crédito encontrada.",
            tags = {"Credit Card"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "La tarjeta de crédito se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Tarjeta de crédito no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findByNumber(@PathVariable("number") String number) {
        try {
            CreditCardResponse response = creditCardInputPort.findCardByNumber(number);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra todas las tarjetas de crédito en el sistema",
            description = "Este endpoint permite buscar todas las tarjetas de crédito en el sistema. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito encontradas.",
            tags = {"Credit Card"},
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
    public ResponseEntity<?> findAllByDefault(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CreditCardResponse> list = creditCardInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra todas las tarjetas de crédito en el sistema con paginación",
            description = "Este endpoint permite buscar todas las tarjetas de crédito en el sistema con paginación. Se requiere el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito encontradas.",
            tags = {"Credit Card"},
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
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CreditCardResponse> list = creditCardInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/type/{type}/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra tarjetas de crédito por tipo en el sistema con paginación",
            description = "Este endpoint permite buscar tarjetas de crédito en el sistema por tipo con paginación. Se requiere el tipo de tarjeta, el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito encontradas.",
            tags = {"Credit Card"},
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
    public ResponseEntity<?> findByType(@PathVariable("type") String type, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<CreditCardResponse> list = creditCardInputPort.findCardsByType(offset, pageSize, type);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/type/{type}")
    @Operation(
            summary = "Encuentra tarjetas de crédito por tipo en el sistema",
            description = "Este endpoint permite buscar tarjetas de crédito en el sistema por tipo. Se requiere el tipo de tarjeta en la ruta de la URL. Devuelve una lista de objetos CreditCardResponse con los detalles de las tarjetas de crédito encontradas.",
            tags = {"Credit Card"},
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
    public ResponseEntity<?> findByTypeByDefault(@PathVariable("type") String type) {
        try {
            List<CreditCardResponse> list = creditCardInputPort.findCardsByType(0, 10, type);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/remove")
    @Operation(
            summary = "Elimina una tarjeta de crédito en el sistema",
            description = "Este endpoint permite eliminar una tarjeta de crédito en el sistema utilizando su ID. Devuelve un estado HTTP 200 si la tarjeta de crédito se eliminó correctamente.",
            tags = {"Credit Card"},
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
                            responseCode = "404",
                            description = "Tarjeta de crédito no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            creditCardInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CreditCardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
