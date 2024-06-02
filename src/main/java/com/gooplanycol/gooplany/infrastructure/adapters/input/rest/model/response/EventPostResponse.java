package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPostResponse {
    private Long id;
    private String title;
    private String description;
    private String eventCategory;
    private String typeOfAudience;
    private String typeOfPlace;
    private boolean isFree;
    private int price;
    private boolean isUnlimited;
    private int capacity;
    private @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime startAt;
    private @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime finishAt;
    private AddressResponse address;
}
