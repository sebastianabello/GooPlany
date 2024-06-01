package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

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

    @Column(columnDefinition = "boolean default true")
    private Boolean isFree;

    @Column(columnDefinition = "decimal default 0")
    private Double price;

    @Column(columnDefinition = "boolean default true")
    private Boolean isUnlimited;

    @Column(columnDefinition = "integer default 0")
    private Integer capacity;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;

    @OneToOne
    @JoinColumn(referencedColumnName = "address_id")
    private AddressEntity address;

}
