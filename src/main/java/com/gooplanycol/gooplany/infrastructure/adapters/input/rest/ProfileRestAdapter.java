package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.ProfileInputPort;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.ProfileRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.ProfileCreateRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.ProfileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileRestAdapter {
    private final ProfileInputPort profileInputPort;
    private final ProfileRestMapper profileRestMapper;

    @GetMapping("/v1/api")
    public List<ProfileResponse> findAll() {
        return profileRestMapper.toProfileResponseList(profileInputPort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public ProfileResponse findById(@PathVariable Long id) {
        return profileRestMapper.toProfileResponse(profileInputPort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<ProfileResponse> save(@Valid @RequestBody ProfileCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileRestMapper.toProfileResponse(profileInputPort.save(profileRestMapper.toProfile(request))));
    }

    @PutMapping("/v1/api/{id}")
    public ProfileResponse update(@PathVariable Long id, @Valid @RequestBody ProfileCreateRequest request) {
        return profileRestMapper.toProfileResponse(profileInputPort.update(id, profileRestMapper.toProfile(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        profileInputPort.deleteById(id);
    }

    @PostMapping("/v1/api/{profileId}/events/{eventId}/register")
    public ResponseEntity<Void> registerToEvent(@PathVariable Long profileId, @PathVariable Long eventId) {
        profileInputPort.registerToEvent(profileId, eventId);
        return ResponseEntity.ok().build();
    }

}
