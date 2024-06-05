package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "even_finished_id")
    private List<EventFinished> eventsFinished;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;
}