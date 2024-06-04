package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.HistoryOutputPort;
import com.gooplanycol.gooplany.domain.exception.HistoryException;
import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventFinishedEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.HistoryEntity;
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
    public History save(History history) {
        if (history != null) {
            HistoryEntity historyEntity = HistoryEntity.builder()
                    .eventsFinished(new ArrayList<>())
                    .modificationDate(history.getModificationDate())
                    .build();
            return historyOutputMapper.toHistory(historyRepository.save(historyEntity));
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
    public History findById(Long id) {
        HistoryEntity historyEntity = historyRepository.findById(id).orElse(null);
        if (historyEntity != null) {
            return historyOutputMapper.toHistory(historyEntity);
        } else {
            throw new HistoryException("The history fetched doesn't exist");
        }
    }

    @Override
    public List<History> findAll(Integer offset, Integer pageSize) {
        Page<HistoryEntity> list = historyRepository.findAll(PageRequest.of(offset, pageSize));
        return list.stream().map(historyOutputMapper::toHistory).collect(Collectors.toList());
    }

    @Override
    public List<EventFinished> findEventFinished(Long id, Integer offset, Integer pageSize) {
        Page<EventFinishedEntity> list = historyRepository.findHistoryEventsFinished(id, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventFinishedOutputMapper::toEventFinished).collect(Collectors.toList());
        } else {
            throw new HistoryException("The list of events finished is null");
        }
    }

    @Override
    @Transactional
    public List<EventFinished> addEventFinished(EventFinished eventFinished, Long id) {
        HistoryEntity historyEntity = historyRepository.findById(id).orElse(null);
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(eventFinished.getId()).orElse(null);
        if (historyEntity != null && eventFinishedEntity != null) {
            historyEntity.getEventsFinished().add(eventFinishedEntity);
            historyEntity.setModificationDate(LocalDate.now());
            historyRepository.save(historyEntity);
            Page<EventFinishedEntity> list = historyRepository.findHistoryEventsFinished(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(eventFinishedOutputMapper::toEventFinished).collect(Collectors.toList());
        } else {
            throw new HistoryException("The history or event finished fetched to add doesn't exist");
        }
    }

    @Override
    public boolean removeEventFinished(Long eventFinishedId, Long historyId) {
        HistoryEntity historyEntity = historyRepository.findById(historyId).orElse(null);
        EventFinishedEntity eventFinishedEntity = eventFinishedRepository.findById(eventFinishedId).orElse(null);
        if (historyEntity != null && eventFinishedEntity != null) {
            historyEntity.getEventsFinished().remove(eventFinishedEntity);
            historyEntity.setModificationDate(LocalDate.now());
            historyRepository.save(historyEntity);
            return true;
        } else {
            throw new HistoryException("The history or event finished fetched to remove doesn't exist");
        }
    }

}
