package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.HistoryCustomerInputPort;
import com.gooplanycol.gooplany.application.ports.output.HistoryCustomerOutputPort;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.HistoryCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryCustomerService implements HistoryCustomerInputPort {

    private final HistoryCustomerOutputPort historyCustomerOutputPort;

    @Override
    public HistoryCustomer save(HistoryCustomer historyCustomer) {
        return historyCustomerOutputPort.save(historyCustomer);
    }

    @Override
    public boolean remove(Long id) {
        return historyCustomerOutputPort.remove(id);
    }

    @Override
    public HistoryCustomer findById(Long id) {
        return historyCustomerOutputPort.findById(id);
    }

    @Override
    public List<HistoryCustomer> findAll(Integer offset, Integer pageSize) {
        return historyCustomerOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<EventPost> findEventPosts(Long id, Integer offset, Integer pageSize) {
        return historyCustomerOutputPort.findEventPosts(id, offset, pageSize);
    }

    @Override
    public List<EventPost> addEventPost(EventPost eventPost, Long id) {
        return historyCustomerOutputPort.addEventPost(eventPost, id);
    }

    @Override
    public boolean removeEvenPosts(Long eventPostId, Long historyId) {
        return historyCustomerOutputPort.removeEvenPosts(eventPostId, historyId);
    }
}
