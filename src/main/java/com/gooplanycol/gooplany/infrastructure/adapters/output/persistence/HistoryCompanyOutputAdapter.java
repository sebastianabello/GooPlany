package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.HistoryCompanyOutputPort;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryCompanyOutputAdapter implements HistoryCompanyOutputPort {

    private final HistoryRepository historyRepository;

    @Override
    public HistoryCompany save(HistoryCompany historyCompany) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public HistoryCompany findById(Long id) {
        return null;
    }

    @Override
    public List<HistoryCompany> findAll(Integer offset, Integer pageSize) {
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
