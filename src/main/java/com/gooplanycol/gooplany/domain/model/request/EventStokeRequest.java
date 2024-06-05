package com.gooplanycol.gooplany.domain.model.request;

import java.time.LocalDateTime;

public record EventStokeRequest(
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
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        AddressRequest address
) {
}