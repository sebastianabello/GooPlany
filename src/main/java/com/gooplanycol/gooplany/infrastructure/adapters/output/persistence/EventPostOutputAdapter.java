package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.AddressEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.EventPostEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventPostOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventPostOutputAdapter implements EventPostOutputPort {

    private final EventPostRepository eventPostRepository;
    private final EventPostOutputMapper eventPostOutputMapper;


    @Override
    public EventPost save(EventPost eventPost) {
        if (eventPost != null) {
            EventPostEntity eventPostSaved = EventPostEntity.builder()
                    .title(eventPost.getTitle())
                    .description(eventPost.getDescription())
                    .eventCategory(eventPost.findEventCategory(eventPost.getEventCategory().name()))
                    .typeOfAudience(eventPost.findTypeOfAudience(eventPost.getTypeOfAudience().name()))
                    .typeOfPlace(eventPost.findTypeOfPlace(eventPost.getTypeOfPlace().name()))
                    .isFree(eventPost.getIsFree())
                    .price(eventPost.getPrice())
                    .isUnlimited(eventPost.getIsUnlimited())
                    .capacity(eventPost.getCapacity())
                    .startAt(eventPost.getStartAt())
                    .finishAt(eventPost.getFinishAt())
                    .address(new AddressEntity(null, eventPost.getAddress().getStreet(), eventPost.getAddress().getCountry(), eventPost.getAddress().getPostalCode()))
                    .build();
            return eventPostOutputMapper.toEventPost(eventPostRepository.save(eventPostSaved));
        } else {
            throw new EventPostException("The event post to save is null");
        }
    }

    @Override
    public EventPost edit(EventPost eventPost, Long id) {
        EventPostEntity e = eventPostRepository.findById(id).orElse(null);
        if (e != null) {
            e.setDescription(eventPost.getDescription());
            e.setEventCategory(eventPost.findEventCategory(eventPost.getEventCategory().name()));
            e.setTypeOfAudience(eventPost.findTypeOfAudience(eventPost.getTypeOfAudience().name()));
            e.setTypeOfPlace(eventPost.findTypeOfPlace(eventPost.getTypeOfPlace().name()));
            e.setStartAt(eventPost.getStartAt());
            e.setFinishAt(eventPost.getFinishAt());
            return eventPostOutputMapper.toEventPost(eventPostRepository.save(e));
        } else {
            throw new EventPostException("The event post to update doesn't exist");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (eventPostRepository.existsById(id)) {
            eventPostRepository.deleteById(id);
            return true;
        } else {
            throw new EventPostException("The event post to delete doesn't exist");
        }
    }

    @Override
    public EventPost findById(Long id) {
        EventPostEntity eventPost = eventPostRepository.findById(id).orElse(null);
        if (eventPost != null) {
            return eventPostOutputMapper.toEventPost(eventPost);
        } else {
            throw new EventPostException("The event post fetched by id doesn't exist");
        }
    }

    @Override
    public List<EventPost> findAll(Integer offset, Integer pageSize) {
        Page<EventPostEntity> list = eventPostRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.stream().map(eventPostOutputMapper::toEventPost).collect(Collectors.toList());
        } else {
            throw new EventPostException("The list of event post post is null");
        }
    }

    @Override
    public List<EventPost> findEventPostByTitle(String title, Integer offset, Integer pageSize) {
        Page<EventPostEntity> list = eventPostRepository.findEventPostByTitle(title, PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(eventPostOutputMapper::toEventPost).collect(Collectors.toList());
        } else {
            throw new EventPostException("The list of event ost fetched by barcode is null");
        }
    }
}
