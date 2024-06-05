package com.gooplanycol.gooplany.domain.model.response;

import java.time.LocalDateTime;

public record EventPostResponse(
        Long id,
        String title,
        String description,
        String eventCategory,
        String typeOfAudience,
        String typeOfPlace,
        boolean isFree,
        int price,
        boolean isUnlimited,
        int capacity,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        AddressResponse address
) {
}
