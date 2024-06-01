package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.HistoryCustomerOutputPort;
import com.gooplanycol.gooplany.domain.exception.HistoryException;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCustomer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryCustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventPostOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryCustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.HistoryCustomerRepository;
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
public class HistoryCustomerOutputAdapter implements HistoryCustomerOutputPort {

    private final HistoryCustomerRepository historyCustomerRepository;
    private final HistoryCustomerOutputMapper historyCustomerOutputMapper;

    private final EventPostRepository eventPostRepository;
    private final EventPostOutputMapper eventPostOutputMapper;

    @Override
    public HistoryCustomer save(HistoryCustomer historyCustomer) {
        if (historyCustomer != null) {
            HistoryCustomerEntity history = HistoryCustomerEntity.builder()
                    .eventPosts(new ArrayList<>())
                    .updateAt(historyCustomer.getUpdateAt())
                    .build();
            return historyCustomerOutputMapper.toHistoryCustomer(historyCustomerRepository.save(history));
        } else {
            throw new HistoryException("The request to save is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (historyCustomerRepository.existsById(id)) {
            historyCustomerRepository.deleteById(id);
            return true;
        } else {
            throw new HistoryException("The history fetched to delete doesn't exist");
        }
    }

    @Override
    public HistoryCustomer findById(Long id) {
        HistoryCustomerEntity history = historyCustomerRepository.findById(id).orElse(null);
        if (history != null) {
            return historyCustomerOutputMapper.toHistoryCustomer(history);
        } else {
            throw new HistoryException("The history fetched by id doesn't exist");
        }
    }

    @Override
    public List<HistoryCustomer> findAll(Integer offset, Integer pageSize) {
        Page<HistoryCustomerEntity> historyPage = historyCustomerRepository.findAll(PageRequest.of(offset, pageSize));
        return historyPage.stream().map(historyCustomerOutputMapper::toHistoryCustomer).collect(Collectors.toList());
    }

    @Override
    public List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize) {
        Page<EventPostEntity> list = historyCustomerRepository.findHistoryEventPosts(id, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventPostOutputMapper::toEventPost).collect(Collectors.toList());
        } else {
            throw new HistoryException("The list of history's sales is null");
        }
    }

    @Override
    public List<EventPost> addEventPost(EventPost eventPost, Long id) {
        HistoryCustomerEntity history = historyCustomerRepository.findById(id).orElse(null);
        EventPostEntity eventPostEntity = eventPostRepository.findById(eventPost.getId()).orElse(null);
        if (history != null && eventPostEntity != null) {
            history.getEventPosts().add(eventPostEntity);
            history.setUpdateAt(LocalDateTime.now());
            historyCustomerRepository.save(history);
            Page<EventPostEntity> list = historyCustomerRepository.findHistoryEventPosts(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(eventPostOutputMapper::toEventPost).collect(Collectors.toList());
        } else {
            throw new HistoryException("The history fetched or the sale to add doesn't exist");
        }
    }

    @Override
    public boolean removeEvenPosts(Long eventPostId, Long historyId) {
        HistoryCustomerEntity history = historyCustomerRepository.findById(historyId).orElse(null);
        EventPostEntity eventPost = eventPostRepository.findById(eventPostId).orElse(null);
        if (history != null && eventPost != null) {
            history.getEventPosts().remove(eventPost);
            history.setUpdateAt(LocalDateTime.now());
            historyCustomerRepository.save(history);
            return true;
        } else {
            throw new HistoryException("The history fetched or the sale to add doesn't exist");
        }
    }
}
