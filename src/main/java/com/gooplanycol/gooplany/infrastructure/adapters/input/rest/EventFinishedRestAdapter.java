package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventFinishedInputPort;
import com.gooplanycol.gooplany.domain.exception.EventFinishedException;
import com.gooplanycol.gooplany.domain.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;
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
@RequestMapping("/api/v1/eventFinished")
public class EventFinishedRestAdapter {

    private final EventFinishedInputPort eventFinishedInputPort;

    @PostMapping("/save")
    @Operation(
            summary = "Guarda un evento finalizado en el sistema",
            description = "Este endpoint permite a los usuarios guardar un evento finalizado en el sistema. Se requiere un cuerpo de solicitud con los detalles del evento finalizado a guardar. Devuelve un objeto EventFinishedResponse con los detalles del evento finalizado guardado.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "El evento finalizado se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
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
    public ResponseEntity<?> save(@RequestBody EventFinishedRequest eventFinishedRequest) {
        try {
            EventFinishedResponse eventFinishedResponse = eventFinishedInputPort.save(eventFinishedRequest);
            return new ResponseEntity<>(eventFinishedResponse, HttpStatus.CREATED);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    @Operation(
            summary = "Edita un evento finalizado en el sistema",
            description = "Este endpoint permite a los usuarios editar un evento finalizado en el sistema utilizando su ID. Se requiere el ID del evento en la ruta de la URL y un cuerpo de solicitud con los detalles del evento finalizado a editar. Devuelve un objeto EventFinishedResponse con los detalles del evento finalizado editado.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El evento finalizado se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
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
                            description = "Evento finalizado no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody EventFinishedRequest saleRequestDTO, @PathVariable("id") Long id) {
        try {
            EventFinishedResponse responseDTO = eventFinishedInputPort.edit(saleRequestDTO, id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Encuentra un evento finalizado en el sistema por su ID",
            description = "Este endpoint permite a los usuarios buscar un evento finalizado en el sistema utilizando su ID. Se requiere el ID del evento en la ruta de la URL. Devuelve un objeto EventFinishedResponse con los detalles del evento finalizado encontrado.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El evento finalizado se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Evento finalizado no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EventFinishedResponse responseDTO = eventFinishedInputPort.findById(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventFinishedResponse> list = eventFinishedInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra todos los eventos finalizados en el sistema con paginación",
            description = "Este endpoint permite a los usuarios buscar todos los eventos finalizados en el sistema utilizando la paginación. Se requieren los parámetros de offset y pageSize en la ruta de la URL. Devuelve una lista de objetos EventFinishedResponse con los detalles de los eventos finalizados encontrados.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los eventos finalizados se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Eventos finalizados no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventFinishedResponse> list = eventFinishedInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add/EventParticipant")
    @Operation(
            summary = "Agrega un participante a un evento finalizado en el sistema",
            description = "Este endpoint permite a los usuarios agregar un participante a un evento finalizado en el sistema utilizando el ID del evento. Se requiere el ID del evento en la ruta de la URL y un cuerpo de solicitud con los detalles del participante a agregar. Devuelve un objeto EventFinishedResponse con los detalles del evento finalizado actualizado.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El participante se ha agregado correctamente al evento finalizado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
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
                            description = "Evento finalizado no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> addEventParticipant(@RequestBody EventParticipantRequest eventParticipantRequest, @PathVariable("id") Long id) {
        try {
            EventFinishedResponse eventFinishedResponse = eventFinishedInputPort.addEventParticipant(eventParticipantRequest, id);
            return new ResponseEntity<>(eventFinishedResponse, HttpStatus.OK);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{eventFinishedId}/remove/eventParticipant/{eventParticipantId}")
    @Operation(
            summary = "Elimina un participante de un evento finalizado en el sistema",
            description = "Este endpoint permite a los usuarios eliminar un participante de un evento finalizado en el sistema utilizando el ID del evento y el ID del participante. Se requiere el ID del evento y el ID del participante en la ruta de la URL. Devuelve un objeto EventFinishedResponse con los detalles del evento finalizado actualizado.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El participante se ha eliminado correctamente del evento finalizado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
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
                            description = "Evento finalizado o participante no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> removeEventParticipant(@PathVariable("eventFinishedId") Long eventFinishedId, @PathVariable("eventParticipantId") Long eventParticipantId) {
        try {
            EventFinishedResponse eventFinishedResponse = eventFinishedInputPort.removeEventParticipant(eventFinishedId, eventParticipantId);
            return new ResponseEntity<>(eventFinishedResponse, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/find/eventParticipants/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra los participantes de un evento finalizado en el sistema con paginación",
            description = "Este endpoint permite a los usuarios buscar los participantes de un evento finalizado en el sistema utilizando el ID del evento y la paginación. Se requiere el ID del evento y los parámetros de offset y pageSize en la ruta de la URL. Devuelve una lista de objetos EventParticipantResponse con los detalles de los participantes encontrados.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los participantes del evento finalizado se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participantes del evento finalizado no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventParticipant(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventParticipantResponse> list = eventFinishedInputPort.findEventParticipants(id, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/find/eventParticipants")
    @Operation(
            summary = "Encuentra los participantes de un evento finalizado en el sistema con paginación por defecto",
            description = "Este endpoint permite a los usuarios buscar los participantes de un evento finalizado en el sistema utilizando el ID del evento y la paginación por defecto. Se requiere el ID del evento en la ruta de la URL. Devuelve una lista de objetos EventParticipantResponse con los detalles de los participantes encontrados.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los participantes del evento finalizado se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventParticipantResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Participantes del evento finalizado no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventParticipantsByDefault(@PathVariable("id") Long id) {
        try {
            List<EventParticipantResponse> list = eventFinishedInputPort.findEventParticipants(id, 0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/eventPost")
    @Operation(
            summary = "Encuentra el post de un evento finalizado en el sistema",
            description = "Este endpoint permite a los usuarios buscar el post de un evento finalizado en el sistema utilizando el ID del evento. Se requiere el ID del evento en la ruta de la URL. Devuelve un objeto EventPostResponse con los detalles del post del evento finalizado encontrado.",
            tags = {"EventFinished"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El post del evento finalizado se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Post del evento finalizado no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventPost(@PathVariable("id") Long id) {
        try {
            EventPostResponse eventPostResponse = eventFinishedInputPort.findEventPost(id);
            return new ResponseEntity<>(eventPostResponse, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
