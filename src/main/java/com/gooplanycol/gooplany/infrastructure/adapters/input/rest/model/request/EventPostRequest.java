package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;


import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CompanyResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventPostRequest(
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
        LocalDate createdAt,
        String statusEventPost,
        AddressRequest addressId,
        CompanyResponse companyId
) {
}
