package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.HistoryOutputPort;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryOutputAdapter implements HistoryOutputPort {

    private final HistoryRepository historyRepository;

    @Override
    public History save(History history) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public History findById(Long id) {
        return null;
    }

    @Override
    public List<History> findAll(Integer offset, Integer pageSize) {
        return List.of();
    }

    @Override
    public List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize) {
        return List.of();
    }

    @Override
    public List<EventPost> addEventPost(EventPost eventPost, Long id) {
        return List.of();
    }

    @Override
    public boolean removeEvenPosts(Long eventPostId, Long historyId) {
        return false;
    }
}
