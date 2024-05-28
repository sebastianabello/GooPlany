package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.StatusEventPost;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPost {
    private Long id;
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
    private StatusEventPost eventStatus;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<Media> images;
    private Address address;
    private Company company;
}
