package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.UserServicePort;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.UserCreateRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestAdapter {

    private final UserServicePort servicePort;
    private final UserRestMapper mapper;

    @GetMapping("/v1/api")
    public List<UserResponse> findAll() {
        return mapper.toUserResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return mapper.toUserResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toUserResponse(servicePort.save(mapper.toUser(request))));
    }

    @PutMapping("/v1/api/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserCreateRequest request) {
        return mapper.toUserResponse(servicePort.update(id, mapper.toUser(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        servicePort.deleteById(id);
    }
}
