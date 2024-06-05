package com.gooplanycol.gooplany.domain.model.response;


import java.time.LocalDate;
import java.util.List;

public record HistoryResponse(
        Long id,
        List<EventFinishedResponse> eventsFinished,
        LocalDate dateModification
) {
}