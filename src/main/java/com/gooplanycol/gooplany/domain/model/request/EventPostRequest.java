package com.gooplanycol.gooplany.domain.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EventPostRequest(
        String title,
        String description,
        String eventCategory,
        String typeOfAudience,
        String typeOfPlace,
        boolean free,
        double price,
        int capacity,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        AddressRequest address
) {
}
