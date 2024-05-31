package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.EventPostOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.EventPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventPostOutputAdapter implements EventPostOutputPort {

    private final EventPostRepository eventPostRepository;
    private final EventPostOutputMapper eventPostOutputMapper;

    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper companyOutputMapper;

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRegistrationOutputMapper eventRegistrationOutputMapper;


    @Override
    public EventPost save(EventPost eventPost) {
        if (eventPost != null) {
            EventPost eventPostSaved = EventPost.builder()
                    .id(eventPost.getId())
                    .title(eventPost.getTitle())
                    .description(eventPost.getDescription())
                    .eventCategory(eventPost.findEventCategory(eventPost.getEventCategory()).name())
                    .typeOfAudience(eventPost.findTypeOfAudience(eventPost.getTypeOfAudience()).name())
                    .typeOfPlace(eventPost.findTypeOfPlace(eventPost.getTypeOfPlace()).name())
                    .isFree(eventPost.getIsFree())
                    .price(eventPost.getPrice())
                    .isUnlimited(eventPost.getIsUnlimited())
                    .capacity(eventPost.getCapacity())
                    .startAt(eventPost.getStartAt())
                    .finishAt(eventPost.getFinishAt())
                    .createdAt(LocalDate.now())
                    .statusEventPost(eventPost.findStatusEventPost(eventPost.getStatusEventPost()).name())
                    .address(eventPost.getAddress())
                    .company(findCompany(eventPost.getCompany().getId()))
                    .build();
            return eventPostOutputMapper.toEventPost(eventPostRepository.save(eventPostOutputMapper.toEventPostEntity(eventPostSaved)));
        } else {
            throw new EventPostException("The event post to save is null");
        }
    }

    private Company findCompany(Long id) {
        return companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
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
    public List<EventPost> findByCompanyId(Long id) {
        List<EventPost> eventPosts = eventPostOutputMapper.toEventPostList(eventPostRepository.findEventPostByCompanyId(id));
        if (eventPosts != null && !eventPosts.isEmpty()) {
            return eventPosts;
        } else {
            throw new EventPostException("The event post fetched by company id doesn't exist");
        }
    }

    @Override
    public List<EventRegistration> findProfilesByEventId(Long id) {
        List<EventRegistration> profiles = eventRegistrationOutputMapper.toEventRegistrationList(eventRegistrationRepository.findAllByEventPostId(id));
        if (profiles != null && !profiles.isEmpty()) {
            return profiles;
        } else {
            throw new EventPostException("The profiles fetched by event id doesn't exist");
        }
    }


    @Override
    public List<EventPost> findAll(Integer offset, Integer pageSize) {
        Page<EventPost> list = eventPostOutputMapper.toEventPostPage(eventPostRepository.findAll(PageRequest.of(offset, pageSize)));
        if (list != null && !list.isEmpty()) {
            return list.stream().collect(Collectors.toList());
        } else {
            throw new EventPostException("The list of event post is null");
        }
    }

    @Override
    public Optional<EventPost> findById(Long id) {
        EventPost eventPost = eventPostOutputMapper.toEventPost(eventPostRepository.findById(id).orElse(null));
        if (eventPost != null) {
            return Optional.of(eventPost);
        } else {
            throw new EventPostException("The event post fetched by id doesn't exist");
        }
    }

    @Override
    public List<EventPost> findEventPostByStatus(Integer offset, Integer pageSize, String statusEventPost) {
        Page<EventPost> list = eventPostOutputMapper.toEventPostPage(eventPostRepository.findEventPostByStatus(statusEventPost, PageRequest.of(offset, pageSize)));
        if (list != null && !list.isEmpty()) {
            return list.stream().collect(Collectors.toList());
        } else {
            throw new EventPostException("The list of event post fetched by status is null");
        }
    }
}
