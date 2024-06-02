package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EventStokeResponse(
        Long id,
        String title,
        String description,
        String eventCategory,
        String typeOfAudience,
        String typeOfPlace,
        boolean isFree,
        double price,
        boolean isUnlimited,
        int capacity,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        String statusEventPost,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        AddressResponse address
) {
}