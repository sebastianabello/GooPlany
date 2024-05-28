package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.EventCategory;
import com.gooplanycol.gooplany.utils.EventPostStatus;
import com.gooplanycol.gooplany.utils.TypeOfAudience;
import com.gooplanycol.gooplany.utils.TypeOfPlace;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "is_free", columnDefinition = "boolean default true")
    private Boolean isFree;

    private BigDecimal price= BigDecimal.ZERO;

    @Column(name = "is_unlimited", columnDefinition = "boolean default true")
    private Boolean isUnlimited;

    private Integer capacity = 0;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "finish_at")
    private LocalDateTime finishAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    private EventPostStatus eventStatus;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;


    @OneToMany(mappedBy = "eventPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaEntity> images;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private CompanyEntity company;

}
