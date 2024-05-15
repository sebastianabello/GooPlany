package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EventRegistrationInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventRegistrationOutputPort;
import com.gooplanycol.gooplany.domain.model.EventRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventRegistrationService implements EventRegistrationInputPort {

    private final EventRegistrationOutputPort eventRegistrationOutputPort;

    @Override
    public EventRegistration save(EventRegistration address) {
        return eventRegistrationOutputPort.save(address);
    }

}
