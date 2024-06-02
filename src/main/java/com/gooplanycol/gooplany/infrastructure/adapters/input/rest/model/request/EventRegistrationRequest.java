package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CustomerResponse;

import java.time.LocalDateTime;

public record EventRegistrationRequest(
        String statusRegistrationEvent,
        LocalDateTime registeredAt,
        CustomerResponse profile,
        EventPostResponse eventId
) {
}
