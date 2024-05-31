package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCompany;

import java.util.List;

public interface HistoryCompanyInputPort {

    HistoryCompany save(HistoryCompany historyCompany);

    boolean remove(Long id);

    HistoryCompany findById(Long id);

    List<HistoryCompany> findAll(Integer offset, Integer pageSize);

    List<EventFinished> findEventFinished(Long id, Integer offset, Integer pageSize);

    List<EventFinished> addEventFinished(EventPost eventPost, Long id);

    boolean removeEventFinished(Long eventPostId, Long historyId);
}
