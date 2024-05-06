package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private String category;
    private Boolean isFree;
    private Double price;
}
