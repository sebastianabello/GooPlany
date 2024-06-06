package com.gooplanycol.gooplany.domain.model.request;

import java.time.LocalDateTime;

public record EventStokeRequest(
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
        boolean enableEvent,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        AddressRequest address
) {
}