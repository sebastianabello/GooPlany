package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.History;

import java.util.List;

public interface HistoryInputPort {

    History save(History history);

    boolean remove(Long id);

    History findById(Long id);

    List<History> findAll(Integer offset, Integer pageSize);

    List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize);

    List<EventPost> addEventPost(EventPost eventPost, Long id);

    boolean removeEvenPosts(Long eventPostId, Long historyId);
}