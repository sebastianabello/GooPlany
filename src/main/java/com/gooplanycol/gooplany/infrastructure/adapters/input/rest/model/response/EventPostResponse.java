package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.gooplanycol.gooplany.domain.model.Company;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPostResponse {
    private String title;
    private String description;
    private String eventCategory;
    private String typeOfAudience;
    private String typeOfPlace;
    private Boolean isFree;
    private String price;
    private Boolean isUnlimited;
    private Integer capacity;
    private String startAt;
    private String finishAt;
    private String eventStatus;
    private String createdAt;
    private AddressResponse addressId;
    private Company companyId;
}
