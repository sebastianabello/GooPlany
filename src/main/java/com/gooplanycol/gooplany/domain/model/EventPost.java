package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import lombok.*;

import java.time.LocalDateTime;

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
    private Double price;
    private Boolean isUnlimited;
    private Integer capacity;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private Address address;

    @Override
    public EventCategory findEventCategory(String eventCategory) {
        return EventCategory.valueOf(eventCategory);
    }

    @Override
    public TypeOfAudience findTypeOfAudience(String typeOfAudience) {
        return TypeOfAudience.valueOf(typeOfAudience);
    }

    @Override
    public TypeOfPlace findTypeOfPlace(String typeOfPlace) {
        return TypeOfPlace.valueOf(typeOfPlace);
    }


}
