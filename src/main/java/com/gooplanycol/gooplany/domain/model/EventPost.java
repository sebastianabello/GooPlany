package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.EventStatus;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPost {
    private UUID id;
    private String title;
    private String description;
    private EventCategory eventCategory;
    private TypeOfAudience typeOfAudience;
    private TypeOfPlace typeOfPlace;
    private Boolean isFree;
    private BigDecimal price;
    private Boolean isUnlimited;
    private Integer capacity;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private EventStatus eventStatus;
    private LocalDateTime createdAt;
    private List<Media> images = new ArrayList<>();
    private Address address;
    private Company company;
}
