package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventRegistration;


public interface EventRegistrationInputPort {

    EventRegistration save(EventRegistration address);

}
