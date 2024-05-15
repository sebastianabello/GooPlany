package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.Profile;

import java.util.List;

public interface EventPostInputPort {

    EventPost save(EventPost eventPost);

    EventPost findById(Long id);

    List<EventPost> findAll();

    EventPost updateEventPost(Long id, EventPost eventPost);

    void deleteById(Long id);

    List<EventPost> findByCompanyId(Long companyId);

    List<Profile> findProfilesByEventId(Long eventId);

}
