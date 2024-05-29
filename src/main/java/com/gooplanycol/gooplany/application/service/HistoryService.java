package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.HistoryInputPort;
import com.gooplanycol.gooplany.application.ports.output.HistoryOutputPort;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryInputPort {

    private final HistoryOutputPort historyOutputPort;

    @Override
    public History save(History history) {
        return historyOutputPort.save(history);
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
    public List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize) {
        return historyOutputPort.findEventPosts(id, offset, pageSize);
    }

    @Override
    public List<EventPost> addEventPost(EventPost eventPost, Long id) {
        return historyOutputPort.addEventPost(eventPost, id);
    }

    @Override
    public boolean removeEvenPosts(Long eventPostId, Long historyId) {
        return historyOutputPort.removeEvenPosts(eventPostId, historyId);
    }
}
