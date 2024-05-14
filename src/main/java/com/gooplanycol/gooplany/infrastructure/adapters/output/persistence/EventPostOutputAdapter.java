package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventPostOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventPostOutputAdapter implements EventPostOutputPort {

    private final EventPostRepository eventPostRepository;
    private final EventPostOutputMapper mapper;

    @Override
    public Optional<EventPost> findById(Long id) {
        return eventPostRepository.findById(id)
                .map(mapper::toEventPost);
    }

    @Override
    public List<EventPost> findAll() {
        return mapper.toEventPostList(eventPostRepository.findAll());
    }

    @Override
    public EventPost save(EventPost eventPost) {
        return mapper.toEventPost(eventPostRepository.save(mapper.toEventPostEntity(eventPost)));
    }

    @Override
    public void deleteById(Long id) {
        eventPostRepository.deleteById(id);
    }
}
