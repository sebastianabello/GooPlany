package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record EmailRequest(
        String subject,
        String text
) {
}
