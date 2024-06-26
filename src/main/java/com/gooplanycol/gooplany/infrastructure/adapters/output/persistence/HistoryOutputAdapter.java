package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.HistoryOutputPort;
import com.gooplanycol.gooplany.domain.exception.HistoryException;
import com.gooplanycol.gooplany.domain.model.request.HistoryRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinished;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.History;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventFinishedOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventFinishedRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.HistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistoryOutputAdapter implements HistoryOutputPort {

    private final HistoryRepository historyRepository;
    private final HistoryOutputMapper historyOutputMapper;

    private final EventFinishedRepository eventFinishedRepository;
    private final EventFinishedOutputMapper eventFinishedOutputMapper;

    @Override
    public HistoryResponse save(HistoryRequest history) {
        if (history != null) {
            History historyEntity = History.builder()
                    .eventsFinished(new ArrayList<>())
                    .dateModification(history.dateModification())
                    .build();
            return historyOutputMapper.toHistoryResponse(historyRepository.save(historyEntity));
        } else {
            throw new HistoryException("The request to save is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (historyRepository.existsById(id)) {
            historyRepository.deleteById(id);
            return true;
        } else {
            throw new HistoryException("The history fetched to delete doesn't exist");
        }
    }

    @Override
    public HistoryResponse findById(Long id) {
        History historyEntity = historyRepository.findById(id).orElse(null);
        if (historyEntity != null) {
            return historyOutputMapper.toHistoryResponse(historyEntity);
        } else {
            throw new HistoryException("The history fetched doesn't exist");
        }
    }

    @Override
    public List<HistoryResponse> findAll(Integer offset, Integer pageSize) {
        Page<History> list = historyRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(historyOutputMapper::toHistoryResponse).collect(Collectors.toList());
    }

    @Override
    public List<EventFinishedResponse> findEventFinished(Long id, Integer offset, Integer pageSize) {
        Page<EventFinished> list = historyRepository.findHistoryEventsFinished(id, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventFinishedOutputMapper::toEventFinishedResponse).collect(Collectors.toList());
        } else {
            throw new HistoryException("The list of events finished is null");
        }
    }

    @Override
    @Transactional
    public List<EventFinishedResponse> addEventFinished(EventFinishedResponse eventFinished, Long id) {
        History historyEntity = historyRepository.findById(id).orElse(null);
        EventFinished eventFinishedEntity = eventFinishedRepository.findById(eventFinished.id()).orElse(null);
        if (historyEntity != null && eventFinishedEntity != null) {
            historyEntity.getEventsFinished().add(eventFinishedEntity);
            historyEntity.setDateModification(LocalDate.now());
            historyRepository.save(historyEntity);
            Page<EventFinished> list = historyRepository.findHistoryEventsFinished(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(eventFinishedOutputMapper::toEventFinishedResponse).collect(Collectors.toList());
        } else {
            throw new HistoryException("The history or event finished fetched to add doesn't exist");
        }
    }

    @Override
    public boolean removeEventFinished(Long eventFinishedId, Long historyId) {
        History historyEntity = historyRepository.findById(historyId).orElse(null);
        EventFinished eventFinishedEntity = eventFinishedRepository.findById(eventFinishedId).orElse(null);
        if (historyEntity != null && eventFinishedEntity != null) {
            historyEntity.getEventsFinished().remove(eventFinishedEntity);
            historyEntity.setDateModification(LocalDate.now());
            historyRepository.save(historyEntity);
            return true;
        } else {
            throw new HistoryException("The history or event finished fetched to remove doesn't exist");
        }
    }

}
