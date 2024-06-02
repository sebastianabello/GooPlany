package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventStokeResponse {
    private Long id;
    private String title;
    private String description;
    private String eventCategory;
    private String typeOfAudience;
    private String typeOfPlace;
    private boolean isFree;
    private double price;
    private boolean isUnlimited;
    private int capacity;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private String statusEventPost;
    private @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    private @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
    private AddressResponse address;
}