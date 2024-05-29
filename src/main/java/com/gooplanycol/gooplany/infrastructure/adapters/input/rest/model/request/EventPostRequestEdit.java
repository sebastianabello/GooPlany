package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.Media;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CompanyResponse;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.StatusEventPost;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record EventPostRequestEdit(
        String description,
        String eventCategory,
        String typeOfAudience,
        String typeOfPlace,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        String statusEventPost,
        LocalDate updatedAt,
        CompanyResponse companyId

) {
}
