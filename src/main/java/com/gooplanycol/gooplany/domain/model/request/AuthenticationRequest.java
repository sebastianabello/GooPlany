package com.gooplanycol.gooplany.domain.model.request;


public record AuthenticationRequest(
        String username,
        String password
) {

}
