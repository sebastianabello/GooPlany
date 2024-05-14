package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.EventPost;

import java.util.List;
import java.util.Optional;

public interface EventPostInputPort {

    EventPost createEventPost(EventPost eventPost);

    EventPost findById(Long id);

    List<EventPost> findAll();

    EventPost updateEventPost(Long id, EventPost eventPost);

    void deleteById(Long id);

    List<EventPost> findByAddress(Address address);


}
