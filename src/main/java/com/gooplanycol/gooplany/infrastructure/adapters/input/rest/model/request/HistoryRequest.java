package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record HistoryRequest(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateModification
) {
}
