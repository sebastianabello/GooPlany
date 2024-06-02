package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_post")
public class EventPostEntity {

    @Id
    @Column(name = "event_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;

    @Enumerated(EnumType.STRING)
    private TypeOfAudience typeOfAudience;

    @Enumerated(EnumType.STRING)
    private TypeOfPlace typeOfPlace;

    private boolean isFree;
    private double price;
    private boolean isUnlimited;
    private int capacity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime finishAt;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
