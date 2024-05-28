package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.gooplanycol.gooplany.utils.EventPostStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventPostResponse(
        Long id,
        String title,
        String description,
        String eventCategory,
        String typeOfAudience,
        String typeOfPlace,
        Boolean isFree,
        String price,
        Boolean isUnlimited,
        Integer capacity,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        EventPostStatus status,
        LocalDate createdAt,
        LocalDate updatedAt,
        String statusEventPost,
        Long addressId,
        Long companyId
) {
}
