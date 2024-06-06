package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.AddressInputPort;
import com.gooplanycol.gooplany.domain.exception.AddressException;
import com.gooplanycol.gooplany.domain.model.request.AddressRequest;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
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
@RequestMapping("/api/v1/address")
public class AddressRestAdapter {

    private final AddressInputPort addressInputPort;


    @PostMapping("/save")
    @Operation(
            summary = "Crea una nueva dirección en el sistema",
            description = "Este endpoint permite crear una nueva dirección. Se requiere un objeto AddressRequest en el cuerpo de la solicitud que contiene los detalles de la dirección a guardar. Devuelve un objeto AddressResponse con los detalles de la dirección guardada.",
            tags = {"Address"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto AddressRequest que contiene los detalles de la dirección a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressRequest.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "La dirección se ha guardado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddressResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Solicitud incorrecta, los detalles de la dirección proporcionados no son válidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> save(@RequestBody AddressRequest addressRequestDTO) {
        try {
            AddressResponse addressResponseDTO = addressInputPort.save(addressRequestDTO);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.CREATED);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    @Operation(
            summary = "Edita una dirección existente en el sistema",
            description = "Este endpoint permite editar una dirección existente. Se requiere un objeto AddressRequest en el cuerpo de la solicitud que contiene los nuevos detalles de la dirección a editar y el ID de la dirección en la ruta de la URL. Devuelve un objeto AddressResponse con los detalles de la dirección editada.",
            tags = {"Address"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto AddressRequest que contiene los nuevos detalles de la dirección a editar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressRequest.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La dirección se ha editado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddressResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Solicitud incorrecta, los detalles de la dirección proporcionados no son válidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la dirección con el ID proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> edit(@RequestBody AddressRequest addressRequestDTO, @PathVariable("id") Long id) {
        try {
            AddressResponse addressResponseDTO = addressInputPort.edit(addressRequestDTO, id);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.CREATED);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/remove")
    @Operation(
            summary = "Elimina una dirección existente en el sistema",
            description = "Este endpoint permite eliminar una dirección existente. Se requiere el ID de la dirección en la ruta de la URL. Devuelve un estado HTTP 200 si la dirección se eliminó correctamente.",
            tags = {"Address"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La dirección se ha eliminado correctamente"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la dirección con el ID proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            addressInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/find/{id}")
    @Operation(
            summary = "Busca una dirección en el sistema por su ID",
            description = "Este endpoint permite buscar una dirección existente por su ID. Se requiere el ID de la dirección en la ruta de la URL. Devuelve un objeto AddressResponse con los detalles de la dirección encontrada.",
            tags = {"Address"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "La dirección se ha encontrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddressResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la dirección con el ID proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            AddressResponse addressResponseDTO = addressInputPort.findById(id);
            return new ResponseEntity<>(addressResponseDTO, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @Operation(
            summary = "Obtiene una lista de direcciones en el sistema",
            description = "Este endpoint permite obtener una lista de direcciones existentes en el sistema. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas. Por defecto, devuelve las primeras 10 direcciones.",
            tags = {"Address"},
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
                            description = "No se encontraron direcciones",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<AddressResponse> list = addressInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    @Operation(
            summary = "Obtiene una lista paginada de direcciones en el sistema",
            description = "Este endpoint obtener una lista paginada de direcciones existentes en el sistema. Se requiere el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas.",
            tags = {"Address"},
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
                            description = "No se encontraron direcciones",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<AddressResponse> list = addressInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/pc/{postalCode}/{offset}/{pageSize}")
    @Operation(
            summary = "Obtiene una lista paginada de direcciones en el sistema por código postal",
            description = "Este endpoint permite obtener una lista paginada de direcciones existentes en el sistema por código postal. Se requiere el código postal, el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas.",
            tags = {"Address"},
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
                            description = "No se encontraron direcciones con el código postal proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findByPostalCode(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable("postalCode") String postalCode) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByPostalCode(offset, pageSize, postalCode);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find/pc/{postalCode}")
    @Operation(
            summary = "Obtiene una lista de direcciones en el sistema por código postal",
            description = "Este endpoint permite obtener una lista de direcciones existentes en el sistema por código postal. Se requiere el código postal en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas. Por defecto, devuelve las primeras 10 direcciones.",
            tags = {"Address"},
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
                            description = "No se encontraron direcciones con el código postal proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findByPostalCodeByDefault(@PathVariable("postalCode") String postalCode) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByPostalCode(0, 10, postalCode);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find/cnt/{country}/{offset}/{pageSize}")
    @Operation(
            summary = "Obtiene una lista paginada de direcciones en el sistema por país",
            description = "Este endpoint permite obtener una lista paginada de direcciones existentes en el sistema por país. Se requiere el país, el offset y el tamaño de la página en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas.",
            tags = {"Address"},
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
                            description = "No se encontraron direcciones con el país proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findByCountry(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable("country") String country) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByCountry(offset, pageSize, country);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/cnt/{country}")
    @Operation(
            summary = "Obtiene una lista de direcciones en el sistema por país",
            description = "Este endpoint permite obtener una lista de direcciones existentes en el sistema por país. Se requiere el país en la ruta de la URL. Devuelve una lista de objetos AddressResponse con los detalles de las direcciones encontradas. Por defecto, devuelve las primeras 10 direcciones.",
            tags = {"Address"},
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
                            description = "No se encontraron direcciones con el país proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> findByCountryByDefault(@PathVariable("country") String country) {
        try {
            List<AddressResponse> list = addressInputPort.findAddressesByCountry(0, 10, country);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (AddressException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}
