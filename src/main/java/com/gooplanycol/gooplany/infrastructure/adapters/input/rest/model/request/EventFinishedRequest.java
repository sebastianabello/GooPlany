package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EventFinishedRequest(
        String concept,
        EventPostRequest eventPost,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt
) {
}