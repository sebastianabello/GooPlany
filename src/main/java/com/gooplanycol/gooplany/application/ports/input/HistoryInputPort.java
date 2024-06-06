package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.HistoryRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;

import java.util.List;

public interface HistoryInputPort {

    HistoryResponse save(HistoryRequest historyRequest);

    boolean remove(Long id);

    HistoryResponse findById(Long id);

    List<HistoryResponse> findAll(Integer offset, Integer pageSize);

    List<EventFinishedResponse> findEventFinished(Long id, Integer offset, Integer pageSize);

    List<EventFinishedResponse> addEventFinished(EventFinishedResponse eventFinished, Long id);

    boolean removeEventFinished(Long idEventFinished, Long idHistory);

}
