package com.gooplanycol.gooplany.domain.model.request;


import java.time.LocalDate;

public record HistoryRequest(
        LocalDate dateModification
) {
}
