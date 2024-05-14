package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.EventPost;

import java.util.List;
import java.util.Optional;

public interface EventPostOutputPort {
    Optional<EventPost> findById(Long id);

    List<EventPost> findAll();

    EventPost save(EventPost eventPost);

    void deleteById(Long id);

}
