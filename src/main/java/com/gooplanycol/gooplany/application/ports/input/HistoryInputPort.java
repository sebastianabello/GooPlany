package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.History;

import java.util.List;

public interface HistoryInputPort {

    History save(History history);

    boolean remove(Long id);

    History findById(Long id);

    List<History> findAll(Integer offset, Integer pageSize);

    List<EventFinished> findEventFinished(Long id, Integer offset, Integer pageSize);

    List<EventFinished> addEventFinished(EventFinished eventFinished, Long id);

    boolean removeEventFinished(Long eventFinishedId, Long historyId);

}
