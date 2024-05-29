package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.EventRegistration;

import java.util.List;
import java.util.Optional;

public interface EventPostOutputPort {


    EventPost save(EventPost eventPost);

    boolean remove(Long id);

    List<EventPost> findByCompanyId(Long id);

    List<EventRegistration> findProfilesByEventId(Long id);

    List<EventPost> findAll(Integer offset, Integer pageSize);

    Optional<EventPost> findById(Long id);

    List<EventPost> findEventPostByStatus(Integer offset, Integer pageSize, String statusEventPost);

}
