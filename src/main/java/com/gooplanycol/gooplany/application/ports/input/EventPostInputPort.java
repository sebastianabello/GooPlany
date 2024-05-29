package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.Profile;

import java.util.List;

public interface EventPostInputPort {

    EventPost save(EventPost eventPost);

    EventPost edit(EventPost eventPost, Long id);

    boolean remove(Long id);

    List<EventPost> findByCompanyId(Long companyId);

    List<Profile> findProfilesByEventId(Long eventId);

    EventPost changeStatus(String status, Long id);

    List<EventPost> findAll(Integer offset, Integer pageSize);

    EventPost findById(Long id);

    List<EventPost> findEventPostByStatus(Integer offset, Integer pageSize, String statusEventPost);

}
