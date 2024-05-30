package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.ProfileInputPort;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.ProfileRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileRestAdapter {
    private final ProfileInputPort profileInputPort;
    private final ProfileRestMapper profileRestMapper;

    @GetMapping("/v1/api/{id}")
    public ProfileResponse findById(@PathVariable Long id) {
        return profileRestMapper.toProfileResponse(profileInputPort.findById(id));
    }



}
