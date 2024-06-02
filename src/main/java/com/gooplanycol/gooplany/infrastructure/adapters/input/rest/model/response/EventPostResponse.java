package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

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
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime finishAt,
        AddressResponse address
) {
}
