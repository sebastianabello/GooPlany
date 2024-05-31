package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.HistoryCompanyOutputPort;
import com.gooplanycol.gooplany.domain.exception.HistoryException;
import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCompany;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventFinishedOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryCompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventFinishedRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.HistoryCompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistoryCompanyOutputAdapter implements HistoryCompanyOutputPort {

    private final HistoryCompanyRepository historyCompanyRepository;
    private final HistoryCompanyOutputMapper historyCompanyOutputMapper;

    private final EventFinishedRepository eventFinishedRepository;
    private final EventFinishedOutputMapper eventFinishedOutputMapper;

    @Override
    public HistoryCompany save(HistoryCompany historyCompany) {
        if (historyCompany != null) {
            HistoryCompanyEntity history = new HistoryCompanyEntity().builder()
                    .eventPosts(new ArrayList<>())
                    .updateAt(historyCompany.getUpdateAt())
                    .build();
            return historyCompanyOutputMapper.toHistoryCompany(historyCompanyRepository.save(history));
        } else {
            throw new HistoryException("The request to save is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (historyCompanyRepository.existsById(id)) {
            historyCompanyRepository.deleteById(id);
            return true;
        } else {
            throw new HistoryException("The history fetched to delete doesn't exist");
        }
    }

    @Override
    public HistoryCompany findById(Long id) {
        HistoryCompanyEntity history = historyCompanyRepository.findById(id).orElse(null);
        if (history != null) {
            return historyCompanyOutputMapper.toHistoryCompany(history);
        } else {
            throw new HistoryException("The history fetched by id doesn't exist");
        }
    }

    @Override
    public List<HistoryCompany> findAll(Integer offset, Integer pageSize) {
        Page<HistoryCompanyEntity> list = historyCompanyRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(historyCompanyOutputMapper::toHistoryCompany).collect(Collectors.toList());
    }

    @Override
    public List<EventFinished> findEventFinished(Long id, Integer offset, Integer pageSize) {
        Page<EventFinishedEntity> list = historyCompanyRepository.findHistoryEventFinished(id, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventFinishedOutputMapper::toEventFinished).collect(Collectors.toList());
        } else {
            throw new HistoryException("The list of history's sales is null");
        }
    }

    @Override
    @Transactional
    public List<EventFinished> addEventFinished(EventPost eventPost, Long id) {
        HistoryCompanyEntity history = historyCompanyRepository.findById(id).orElse(null);
        EventFinishedEntity eventFinished = eventFinishedRepository.findById(eventPost.getId()).orElse(null);
        if (history != null && eventFinished != null) {
            history.getEventPosts().add(eventFinished);
            history.setUpdateAt(LocalDateTime.now());
            historyCompanyRepository.save(history);
            Page<EventFinishedEntity> list = historyCompanyRepository.findHistoryEventFinished(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(eventFinishedOutputMapper::toEventFinished).collect(Collectors.toList());
        } else {
            throw new HistoryException("The history fetched or the sale to add doesn't exist");
        }
    }

    @Override
    public boolean removeEventFinished(Long eventPostId, Long historyId) {
        HistoryCompanyEntity history = historyCompanyRepository.findById(historyId).orElse(null);
        EventFinishedEntity eventFinished = eventFinishedRepository.findById(eventPostId).orElse(null);
        if (history != null && eventFinished != null) {
            history.getEventPosts().remove(eventFinished);
            history.setUpdateAt(LocalDateTime.now());
            historyCompanyRepository.save(history);
            return true;
        } else {
            throw new HistoryException("The history fetched or the sale fetched doesn't exist");
        }
    }


}
