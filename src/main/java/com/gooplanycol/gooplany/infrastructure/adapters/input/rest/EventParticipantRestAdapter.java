package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventParticipantInputPort;
import com.gooplanycol.gooplany.domain.exception.EventParticipantException;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
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
@RequestMapping("/api/v1/eventParticipant")
public class EventParticipantRestAdapter {

    private final EventParticipantInputPort eventParticipantInputPort;

    @PostMapping("/save")
    @Operation(
            summary = "Guarda un nuevo participante de evento en el sistema",
            description = "Este endpoint permite a los usuarios guardar un nuevo participante de evento en el sistema. Se requiere un cuerpo de solicitud con los detalles del participante del evento a guardar. Devuelve un objeto EventParticipantResponse con los detalles del participante del evento guardado.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El participante del evento se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
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
    public ResponseEntity<?> save(@RequestBody EventParticipantRequest eventParticipantRequest) {
        try {
            EventParticipantResponse eventParticipantResponse = eventParticipantInputPort.save(eventParticipantRequest);
            return new ResponseEntity<>(eventParticipantResponse, HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit/{id}")
    @Operation(
            summary = "Edita un participante de evento existente en el sistema",
            description = "Este endpoint permite a los usuarios editar un participante de evento existente en el sistema. Se requiere un cuerpo de solicitud con los detalles del participante del evento a editar y el ID del participante del evento en la ruta de la URL. Devuelve un objeto EventParticipantResponse con los detalles del participante del evento editado.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El participante del evento se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Solicitud incorrecta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participante del evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody EventParticipantRequest eventParticipantRequest, @PathVariable Long id) {
        try {
            EventParticipantResponse paymentResponseDTO = eventParticipantInputPort.edit(eventParticipantRequest, id);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change/status/{status}/{id}")
    @Operation(
            summary = "Cambia el estado de un participante de evento en el sistema",
            description = "Este endpoint permite a los usuarios cambiar el estado de un participante de evento existente en el sistema. Se requiere el estado deseado y el ID del participante del evento en la ruta de la URL. Devuelve un objeto EventParticipantResponse con los detalles del participante del evento actualizado.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El estado del participante del evento se ha cambiado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Solicitud incorrecta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participante del evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> changeStatus(@PathVariable String status, @PathVariable long id) {
        try {
            EventParticipantResponse paymentResponseDTO = eventParticipantInputPort.changeStatus(status, id);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/id/{id}")
    @Operation(
            summary = "Encuentra un participante de evento en el sistema por su ID",
            description = "Este endpoint permite a los usuarios buscar un participante de evento en el sistema utilizando el ID del participante. Se requiere el ID del participante en la ruta de la URL. Devuelve un objeto EventParticipantResponse con los detalles del participante del evento encontrado.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El participante del evento se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participante del evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            EventParticipantResponse paymentResponseDTO = eventParticipantInputPort.findById(id);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra todos los participantes de eventos en el sistema con paginación por defecto",
            description = "Este endpoint permite a los usuarios buscar todos los participantes de eventos en el sistema utilizando la paginación por defecto. No se requiere ningún parámetro en la ruta de la URL. Devuelve una lista de objetos EventParticipantResponse con los detalles de los participantes del evento encontrados.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los participantes del evento se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participantes del evento no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventParticipantResponse> list = eventParticipantInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra todos los participantes de eventos en el sistema con paginación personalizada",
            description = "Este endpoint permite a los usuarios buscar todos los participantes de eventos en el sistema utilizando la paginación personalizada. Se requiere los parámetros de offset y pageSize en la ruta de la URL. Devuelve una lista de objetos EventParticipantResponse con los detalles de los participantes del evento encontrados.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los participantes del evento se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participantes del evento no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventParticipantResponse> list = eventParticipantInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove/{id}")
    @Operation(
            summary = "Elimina un participante de evento en el sistema",
            description = "Este endpoint permite a los usuarios eliminar un participante de evento existente en el sistema. Se requiere el ID del participante del evento en la ruta de la URL. No devuelve contenido en el cuerpo de la respuesta.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El participante del evento se ha eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participante del evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            eventParticipantInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra los participantes de eventos en el sistema por estado con paginación personalizada",
            description = "Este endpoint permite a los usuarios buscar los participantes de eventos en el sistema utilizando el estado del participante y la paginación personalizada. Se requiere el estado del participante y los parámetros de offset y pageSize en la ruta de la URL. Devuelve una lista de objetos EventParticipantResponse con los detalles de los participantes del evento encontrados.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los participantes del evento se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participantes del evento no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventParticipantByStatus(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String status) {
        try {
            List<EventParticipantResponse> list = eventParticipantInputPort.findEventParticipantsByStatus(offset, pageSize, status);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}")
    @Operation(
            summary = "Encuentra los participantes de eventos en el sistema por estado con paginación por defecto",
            description = "Este endpoint permite a los usuarios buscar los participantes de eventos en el sistema utilizando el estado del participante y la paginación por defecto. Se requiere el estado del participante en la ruta de la URL. Devuelve una lista de objetos EventParticipantResponse con los detalles de los participantes del evento encontrados.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los participantes del evento se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participantes del evento no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventParticipantByStatusDefault(@PathVariable String status) {
        try {
            List<EventParticipantResponse> list = eventParticipantInputPort.findEventParticipantsByStatus(0, 10, status);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/customer")
    @Operation(
            summary = "Encuentra el cliente de un participante de evento en el sistema por el ID del participante",
            description = "Este endpoint permite a los usuarios buscar el cliente de un participante de evento en el sistema utilizando el ID del participante. Se requiere el ID del participante en la ruta de la URL. Devuelve un objeto CustomerResponse con los detalles del cliente encontrado.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El cliente del participante del evento se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cliente del participante del evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventParticipantCustomer(@PathVariable Long id) {
        try {
            CustomerResponse customerResponseDTO = eventParticipantInputPort.findCustomerEventParticipant(id);
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/card")
    @Operation(
            summary = "Encuentra la tarjeta de crédito de un participante de evento en el sistema por el ID del participante",
            description = "Este endpoint permite a los usuarios buscar la tarjeta de crédito de un participante de evento en el sistema utilizando el ID del participante. Se requiere el ID del participante en la ruta de la URL. Devuelve un objeto CreditCardResponse con los detalles de la tarjeta de crédito encontrada.",
            tags = {"EventParticipant"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "La tarjeta de crédito del participante del evento se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditCardResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Tarjeta de crédito del participante del evento no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventParticipantCard(@PathVariable Long id) {
        try {
            CreditCardResponse creditCardResponseDTO = eventParticipantInputPort.findCardEventParticipant(id);
            return new ResponseEntity<>(creditCardResponseDTO, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
