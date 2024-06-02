package com.gooplanycol.gooplany.domain.model;

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
    private List<EventFinished> eventsFinished;
    private LocalDate modificationDate;
}
