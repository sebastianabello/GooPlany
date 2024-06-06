package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.request.EventPostRequest;
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
@RequestMapping("/api/v1/eventPost")
public class EventPostRestAdapter {

    private final EventPostInputPort eventPostInputPort;

    @PostMapping("/save")
    @Operation(
            summary = "Guarda un nuevo post de evento en el sistema",
            description = "Este endpoint permite guardar un nuevo post de evento en el sistema. Se requiere un objeto EventPostRequest en el cuerpo de la solicitud. Devuelve un objeto EventPostResponse con los detalles del post de evento guardado.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "El post de evento se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
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
    public ResponseEntity<?> save(@RequestBody EventPostRequest eventPostRequest) {
        try {
            EventPostResponse productSoldResponseDTO = eventPostInputPort.save(eventPostRequest);
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.CREATED);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    @Operation(
            summary = "Edita un post de evento existente en el sistema",
            description = "Este endpoint permite editar un post de evento existente en el sistema. Se requiere un objeto EventPostRequest en el cuerpo de la solicitud y el ID del post de evento en la ruta de la URL. Devuelve un objeto EventPostResponse con los detalles del post de evento editado.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El post de evento se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Post de evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody EventPostRequest eventPostRequest, @PathVariable("id") Long id) {
        try {
            EventPostResponse productSoldResponseDTO = eventPostInputPort.edit(eventPostRequest, id);
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.OK);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/remove")
    @Operation(
            summary = "Elimina un post de evento existente en el sistema",
            description = "Este endpoint permite eliminar un post de evento existente en el sistema. Se requiere el ID del post de evento en la ruta de la URL. No devuelve contenido en la respuesta.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El post de evento se ha eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Post de evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            eventPostInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Encuentra un post de evento en el sistema por el ID del post",
            description = "Este endpoint permite buscar un post de evento en el sistema utilizando el ID del post. Se requiere el ID del post en la ruta de la URL. Devuelve un objeto EventPostResponse con los detalles del post de evento encontrado.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "El post de evento se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Post de evento no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EventPostResponse productSoldResponseDTO = eventPostInputPort.findById(id);
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra una lista de posts de eventos en el sistema",
            description = "Este endpoint permite buscar una lista de posts de eventos en el sistema utilizando un offset y un tamaño de página. Se requieren el offset y el tamaño de página en la ruta de la URL. Devuelve una lista de objetos EventPostResponse con los detalles de los posts de eventos encontrados.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los posts de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Posts de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventPostResponse> list = eventPostInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Encuentra una lista de posts de eventos en el sistema con valores predeterminados",
            description = "Este endpoint permite buscar una lista de posts de eventos en el sistema utilizando un offset y un tamaño de página predeterminados (0 y 10 respectivamente). No se requieren parámetros en la ruta de la URL. Devuelve una lista de objetos EventPostResponse con los detalles de los posts de eventos encontrados.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los posts de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Posts de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventPostResponse> list = eventPostInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/title/{title}/{offset}/{pageSize}")
    @Operation(
            summary = "Encuentra una lista de posts de eventos en el sistema por título",
            description = "Este endpoint permite buscar una lista de posts de eventos en el sistema utilizando el título del post, un offset y un tamaño de página. Se requieren el título del post, el offset y el tamaño de página en la ruta de la URL. Devuelve una lista de objetos EventPostResponse con los detalles de los posts de eventos encontrados.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los posts de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Posts de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventsPostByTitle(@PathVariable("title") String title, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventPostResponse> list = eventPostInputPort.findEventPostByTitle(title, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/title/{title}")
    @Operation(
            summary = "Encuentra una lista de posts de eventos en el sistema por título con valores predeterminados",
            description = "Este endpoint permite buscar una lista de posts de eventos en el sistema utilizando el título del post, un offset y un tamaño de página predeterminados (0 y 10 respectivamente). Se requiere el título del post en la ruta de la URL. Devuelve una lista de objetos EventPostResponse con los detalles de los posts de eventos encontrados.",
            tags = {"EventPost"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "302",
                            description = "Los posts de eventos se han encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventPostResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Posts de eventos no encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findEventsPostByTitleByDefault(@PathVariable("title")String title){
        try {
            List<EventPostResponse> list = eventPostInputPort.findEventPostByTitle(title,0, 10);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (EventPostException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
