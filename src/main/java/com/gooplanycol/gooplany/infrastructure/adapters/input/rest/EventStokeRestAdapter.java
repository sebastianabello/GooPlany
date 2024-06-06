package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventStokeInputPort;
import com.gooplanycol.gooplany.domain.exception.EventStokeException;
import com.gooplanycol.gooplany.domain.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.domain.model.response.EventStokeResponse;
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
@RequestMapping("/api/v1/eventStock")
public class EventStokeRestAdapter {

    private final EventStokeInputPort eventStokeInputPort;

    @PostMapping("/save")
    @Operation(
            summary = "Guarda un nuevo stock de eventos en el sistema",
            description = "Este endpoint permite a los usuarios guardar un nuevo stock de eventos en el sistema. Se requiere un objeto EventStokeRequest en el cuerpo de la solicitud. Devuelve un objeto EventStokeResponse con los detalles del stock de eventos guardado.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "El stock de eventos se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
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
    public ResponseEntity<?> save(@RequestBody EventStokeRequest eventStokeRequest) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeInputPort.save(eventStokeRequest);
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.CREATED);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/remove")
    @Operation(
            summary = "Elimina un stock de eventos en el sistema",
            description = "Este endpoint permite a los usuarios eliminar un stock de eventos en el sistema. Se requiere el id del stock de eventos en la ruta de la URL. No devuelve contenido en caso de éxito.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El stock de eventos se ha eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stock de eventos no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            eventStokeInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/edit")
    @Operation(
            summary = "Edita un stock de eventos existente en el sistema",
            description = "Este endpoint permite a los usuarios editar un stock de eventos existente en el sistema. Se requiere un objeto EventStokeRequest en el cuerpo de la solicitud y el id del stock de eventos en la ruta de la URL. Devuelve un objeto EventStokeResponse con los detalles del stock de eventos editado.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El stock de eventos se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stock de eventos no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody EventStokeRequest eventStokeRequest, @PathVariable("id") Long id) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeInputPort.edit(eventStokeRequest, id);
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.OK);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra una lista de stocks de eventos en el sistema con valores predeterminados",
            description = "Este endpoint permite a los usuarios buscar una lista de stocks de eventos en el sistema utilizando un offset y un tamaño de página predeterminados (0 y 10 respectivamente). No se requieren parámetros en la ruta de la URL. Devuelve una lista de objetos EventStokeResponse con los detalles de los stocks de eventos encontrados.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los stocks de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stocks de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventStokeResponse> list = eventStokeInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra una lista de stocks de eventos en el sistema",
            description = "Este endpoint permite a los usuarios buscar una lista de stocks de eventos en el sistema utilizando un offset y un tamaño de página especificados en la ruta de la URL. Devuelve una lista de objetos EventStokeResponse con los detalles de los stocks de eventos encontrados.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los stocks de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stocks de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventStokeResponse> list = eventStokeInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Encuentra un stock de eventos en el sistema por su ID",
            description = "Este endpoint permite a los usuarios buscar un stock de eventos en el sistema utilizando el ID del stock de eventos en la ruta de la URL. Devuelve un objeto EventStokeResponse con los detalles del stock de eventos encontrado.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El stock de eventos se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stock de eventos no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeInputPort.findEventPostById(id);
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}")
    @Operation(
            summary = "Encuentra una lista de stocks de eventos en el sistema por su estado",
            description = "Este endpoint permite a los usuarios buscar una lista de stocks de eventos en el sistema utilizando el estado del stock de eventos en la ruta de la URL. Devuelve una lista de objetos EventStokeResponse con los detalles de los stocks de eventos encontrados.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los stocks de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stocks de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventStokesByEnableEventPostByDefault(@PathVariable String status) {
        try {
            List<EventStokeResponse> list = eventStokeInputPort.findEventStokesByEnableEventPost(status, 0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra una lista de stocks de eventos en el sistema por su estado",
            description = "Este endpoint permite a los usuarios buscar una lista de stocks de eventos en el sistema utilizando el estado del stock de eventos y un offset y un tamaño de página especificados en la ruta de la URL. Devuelve una lista de objetos EventStokeResponse con los detalles de los stocks de eventos encontrados.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los stocks de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stocks de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventsStockByEnableEventPost(@PathVariable String status, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventStokeResponse> list = eventStokeInputPort.findEventStokesByEnableEventPost(status, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/title/{title}")
    @Operation(
            summary = "Encuentra un stock de eventos en el sistema por su título",
            description = "Este endpoint permite a los usuarios buscar un stock de eventos en el sistema utilizando el título del stock de eventos en la ruta de la URL. Devuelve un objeto EventStokeResponse con los detalles del stock de eventos encontrado.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El stock de eventos se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stock de eventos no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventsStockByTitle(@PathVariable("title") String title) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeInputPort.findEventStockByTitle(title);
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/participate/{amount}")
    @Operation(
            summary = "Participa en un stock de eventos en el sistema",
            description = "Este endpoint permite a los usuarios participar en un stock de eventos en el sistema. Se requiere el id del stock de eventos y la cantidad de participaciones en la ruta de la URL. Devuelve un objeto EventStokeResponse con los detalles del stock de eventos después de la participación.",
            tags = {"EventStoke"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La participación en el stock de eventos se ha realizado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventStokeResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Stock de eventos no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> participateEvent(@PathVariable int amount, @PathVariable long id) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeInputPort.participateEvent(amount, id);
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.OK);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
