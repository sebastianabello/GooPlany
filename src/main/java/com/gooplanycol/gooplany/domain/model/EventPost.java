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
public class EventPost extends Utils {
    private Long id;
    private String title;
    private String description;
    private String eventCategory;
    private String typeOfAudience;
    private String typeOfPlace;
    private Boolean isFree;
    private BigDecimal price;
    private Boolean isUnlimited;
    private Integer capacity;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private String statusEventPost;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<Media> images;
    private Address address;
    private Company company;

    public EventCategory findEventCategory(String eventCategory) {
        return EventCategory.valueOf(eventCategory);
    }

    public TypeOfAudience findTypeOfAudience(String typeOfAudience) {
        return TypeOfAudience.valueOf(typeOfAudience);
    }

    public TypeOfPlace findTypeOfPlace(String typeOfPlace) {
        return TypeOfPlace.valueOf(typeOfPlace);
    }

    public StatusEventPost findStatusEventPost(String statusEventPost) {
        return StatusEventPost.valueOf(statusEventPost);
    }


}
