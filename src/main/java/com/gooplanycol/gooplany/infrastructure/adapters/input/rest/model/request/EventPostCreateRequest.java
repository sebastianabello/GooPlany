package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.utils.Country;
import com.gooplanycol.gooplany.utils.TypeOfStreet;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPostCreateRequest {
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
    private Address addressId;
    private Long companyId;
}
