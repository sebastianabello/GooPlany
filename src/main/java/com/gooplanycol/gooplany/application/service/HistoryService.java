package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.HistoryInputPort;
import com.gooplanycol.gooplany.application.ports.output.HistoryOutputPort;
import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryInputPort {

    private final HistoryOutputPort historyOutputPort;

    @Override
    public History save(History historyCustomer) {
        return historyOutputPort.save(historyCustomer);
    }

    @Override
    public boolean remove(Long id) {
        return historyOutputPort.remove(id);
    }

    @Override
    public History findById(Long id) {
        return historyOutputPort.findById(id);
    }

    @Override
    public List<History> findAll(Integer offset, Integer pageSize) {
        return historyOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<EventFinished> findEventFinished(Long id, Integer offset, Integer pageSize) {
        return historyOutputPort.findEventFinished(id, offset, pageSize);
    }

    @Override
    public List<EventFinished> addEventFinished(EventFinished eventFinished, Long id) {
        return historyOutputPort.addEventFinished(eventFinished, id);
    }

    @Override
    public boolean removeEventFinished(Long eventFinishedId, Long historyId) {
        return historyOutputPort.removeEventFinished(eventFinishedId, historyId);
    }


}
