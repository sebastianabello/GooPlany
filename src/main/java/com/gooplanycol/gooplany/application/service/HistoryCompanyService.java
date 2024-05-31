package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.HistoryCompanyInputPort;
import com.gooplanycol.gooplany.application.ports.output.HistoryCompanyOutputPort;
import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryCompanyService implements HistoryCompanyInputPort {

    private final HistoryCompanyOutputPort historyCompanyOutputPort;

    @Override
    public HistoryCompany save(HistoryCompany historyCompany) {
        return historyCompanyOutputPort.save(historyCompany);
    }

    @Override
    public boolean remove(Long id) {
        return historyCompanyOutputPort.remove(id);
    }

    @Override
    public HistoryCompany findById(Long id) {
        return historyCompanyOutputPort.findById(id);
    }

    @Override
    public List<HistoryCompany> findAll(Integer offset, Integer pageSize) {
        return historyCompanyOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<EventFinished> findEventFinished(Long id, Integer offset, Integer pageSize) {
        return historyCompanyOutputPort.findEventFinished(id, offset, pageSize);
    }

    @Override
    public List<EventFinished> addEventFinished(EventPost eventPost, Long id) {
        return historyCompanyOutputPort.addEventFinished(eventPost, id);
    }

    @Override
    public boolean removeEventFinished(Long eventPostId, Long historyId) {
        return historyCompanyOutputPort.removeEventFinished(eventPostId, historyId);
    }

}
