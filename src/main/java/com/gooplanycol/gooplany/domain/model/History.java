package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Long id;
    private List<EventPostEntity> eventPosts;
    private LocalDate modificationDate;
}
