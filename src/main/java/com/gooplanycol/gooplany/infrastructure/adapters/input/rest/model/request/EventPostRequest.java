package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPostRequest {
    private String title;
    private String description;
    private String eventCategory;
    private String typeOfAudience;
    private String typeOfPlace;
    private boolean isFree;
    private int price;
    private boolean isUnlimited;
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startAt;
    private @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime finishAt;
    private AddressRequest address;
}
