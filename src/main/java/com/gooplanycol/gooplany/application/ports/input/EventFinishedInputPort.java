package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventFinished;
import com.gooplanycol.gooplany.domain.model.EventParticipant;
import com.gooplanycol.gooplany.domain.model.EventPost;

import java.util.List;

public interface EventFinishedInputPort {
    EventFinished save(EventFinished eventFinished);

    EventFinished edit(EventFinished eventFinished, Long id);

    EventFinished findById(Long id);

    List<EventFinished> findAll(Integer offset, Integer pageSize);

    EventFinished addEventPost(EventPost eventPost, Long id);

    EventFinished removeEventPost(Long eventPostId, Long eventFinishedId);

    List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize);

    EventParticipant findEventParticipant(Long id);
}
