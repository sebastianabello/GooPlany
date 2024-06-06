package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.HistoryInputPort;
import com.gooplanycol.gooplany.domain.exception.HistoryException;
import com.gooplanycol.gooplany.domain.model.request.HistoryRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
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
@RequestMapping("/api/v1/history")
public class HistoryRestAdapter {

    private final HistoryInputPort historyInputPort;

    @PostMapping("/save")
    @Operation(
            summary = "Guarda un nuevo historial en el sistema",
            description = "Este endpoint permite a los usuarios guardar un nuevo historial en el sistema. Se requiere un objeto HistoryRequest en el cuerpo de la solicitud. Devuelve un objeto HistoryResponse con los detalles del historial guardado.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "El historial se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HistoryResponse.class)
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
    public ResponseEntity<?> save(@RequestBody HistoryRequest historyRequestDTO) {
        try {
            HistoryResponse historyResponseDTO = historyInputPort.save(historyRequestDTO);
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.CREATED);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/remove")
    @Operation(
            summary = "Elimina un historial en el sistema",
            description = "Este endpoint permite a los usuarios eliminar un historial en el sistema utilizando el id del historial en la ruta de la URL. No devuelve contenido en caso de éxito.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El historial se ha eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Historial no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            historyInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Encuentra un historial en el sistema por su ID",
            description = "Este endpoint permite a los usuarios buscar un historial en el sistema utilizando el ID del historial en la ruta de la URL. Devuelve un objeto HistoryResponse con los detalles del historial encontrado.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El historial se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HistoryResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Historial no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            HistoryResponse historyResponseDTO = historyInputPort.findById(id);
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra todos los historiales en el sistema con paginación",
            description = "Este endpoint permite a los usuarios buscar todos los historiales en el sistema utilizando la paginación. Los parámetros de offset y pageSize se pasan en la ruta de la URL. Devuelve una lista de objetos HistoryResponse con los detalles de los historiales encontrados.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los historiales se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HistoryResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Historiales no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<HistoryResponse> list = historyInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra todos los historiales en el sistema con paginación por defecto",
            description = "Este endpoint permite a los usuarios buscar todos los historiales en el sistema utilizando la paginación por defecto (offset 0, pageSize 10). Devuelve una lista de objetos HistoryResponse con los detalles de los historiales encontrados.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los historiales se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HistoryResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Historiales no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<HistoryResponse> list = historyInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/find/eventFinished/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra los eventos finalizados de un historial con paginación",
            description = "Este endpoint permite a los usuarios buscar los eventos finalizados de un historial en el sistema utilizando la paginación. Los parámetros de offset y pageSize se pasan en la ruta de la URL. Devuelve una lista de objetos EventFinishedResponse con los detalles de los eventos finalizados encontrados.",
            tags = {"History"},
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
    public ResponseEntity<?> findEventFinished(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventFinishedResponse> list = historyInputPort.findEventFinished(id, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/find/eventFinished")
    @Operation(
            summary = "Encuentra los eventos finalizados de un historial con paginación por defecto",
            description = "Este endpoint permite a los usuarios buscar los eventos finalizados de un historial en el sistema utilizando la paginación por defecto (offset 0, pageSize 10). Los parámetros de offset y pageSize se pasan en la ruta de la URL. Devuelve una lista de objetos EventFinishedResponse con los detalles de los eventos finalizados encontrados.",
            tags = {"History"},
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
    public ResponseEntity<?> findEventFinishedByDefault(@PathVariable("id") Long id) {
        try {
            List<EventFinishedResponse> list = historyInputPort.findEventFinished(id, 0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add/eventFinished")
    @Operation(
            summary = "Agrega un evento finalizado a un historial",
            description = "Este endpoint permite a los usuarios agregar un evento finalizado a un historial en el sistema. Se requiere un objeto EventFinishedResponse en el cuerpo de la solicitud y el ID del historial en la ruta de la URL. Devuelve una lista de objetos EventFinishedResponse con los detalles de los eventos finalizados del historial.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El evento finalizado se ha agregado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventFinishedResponse.class)
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
    public ResponseEntity<?> addEventFinished(@RequestBody EventFinishedResponse eventFinished, @PathVariable("id") Long id) {
        try {
            List<EventFinishedResponse> list = historyInputPort.addEventFinished(eventFinished, id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{historyId}/remove/eventFinished/{eventFinishedId}")
    @Operation(
            summary = "Elimina un evento finalizado de un historial",
            description = "Este endpoint permite a los usuarios eliminar un evento finalizado de un historial en el sistema. Se requiere el ID del historial y el ID del evento finalizado en la ruta de la URL. No devuelve contenido en caso de éxito.",
            tags = {"History"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El evento finalizado se ha eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Historial o evento finalizado no encontrado",
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
    public ResponseEntity<?> removeEventFinished(@PathVariable("eventFinishedId") Long eventFinishedId, @PathVariable("historyId") Long historyId) {
        try {
            historyInputPort.removeEventFinished(eventFinishedId, historyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
