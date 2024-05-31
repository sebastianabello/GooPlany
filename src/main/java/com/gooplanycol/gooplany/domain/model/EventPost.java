package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.StatusEventPost;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventPost extends Utils {
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
    private StatusEventPost statusEventPost;
    private List<Media> images;
    private Address address;
    private boolean enableEventPost;

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
