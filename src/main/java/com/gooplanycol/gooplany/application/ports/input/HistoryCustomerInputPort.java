package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCustomer;

import java.util.List;

public interface HistoryCustomerInputPort {

    HistoryCustomer save(HistoryCustomer historyCustomer);

    boolean remove(Long id);

    HistoryCustomer findById(Long id);

    List<HistoryCustomer> findAll(Integer offset, Integer pageSize);

    List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize);

    List<EventPost> addEventPost(EventPost eventPost, Long id);

    boolean removeEvenPosts(Long eventPostId, Long historyId);

}
