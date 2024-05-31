package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventPost;

import java.util.List;


public interface EventPostInputPort {

    EventPost save(EventPost eventPost);

    EventPost edit(EventPost eventPost, Long id);

    boolean remove(Long id);

    EventPost findById(Long id);

    List<EventPost> findAll(Integer offset, Integer pageSize);

    List<EventPost> findEventPostByTitle(String title, Integer offset, Integer pageSize);


}
