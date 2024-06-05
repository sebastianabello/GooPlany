package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.request.EventPostRequest;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;

import java.util.List;

public interface EventPostInputPort {

    EventPostResponse save(EventPostRequest eventPost);

    EventPostResponse edit(EventPostRequest eventPost, Long id);

    boolean remove(Long id);

    EventPostResponse findById(Long id);

    List<EventPostResponse> findAll(Integer offset, Integer pageSize);

    List<EventPostResponse> findEventPostByTitle(String title, Integer offset, Integer pageSize);


}
