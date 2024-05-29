package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EmailInputPort;
import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.EventPostRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.ProfileRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EmailRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventPostRestAdapter {

    private final EventPostInputPort eventPostInputPort;
    private final EventPostRestMapper eventPostRestMapper;
    private final ProfileRestMapper profileRestMapper;
    private final EmailInputPort emailInputPort;

    @GetMapping("/v1/api")
    public List<EventPostResponse> findAll() {
        return eventPostRestMapper.toEventPostResponseList(eventPostInputPort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public EventPostResponse findById(@PathVariable Long id) {
        return eventPostRestMapper.toEventPostResponse(eventPostInputPort.findById(id));
    }

    @GetMapping("/v1/api/{eventId}/profiles")
    public ResponseEntity<List<ProfileResponse>> getProfilesByEventId(@PathVariable Long eventId) {
        // List<Profile> profiles = eventPostInputPort.findProfilesByEventId(eventId);
        // return ResponseEntity.ok(profileRestMapper.toProfileResponseList(profiles));
        return null;
    }

    @PostMapping("/v1/api/{eventId}/notify")
    public void notifyProfiles(@PathVariable Long eventId, @RequestBody EmailRequest request) {
        emailInputPort.sendEmailToProfiles(eventId, request.getSubject(), request.getText());
    }
}
