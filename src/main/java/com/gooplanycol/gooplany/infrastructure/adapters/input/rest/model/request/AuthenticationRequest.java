package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

public record AuthenticationRequest(
        String username,
        String password
) {
}
