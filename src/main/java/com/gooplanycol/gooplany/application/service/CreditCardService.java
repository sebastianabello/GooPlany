package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CreditCardInputPort;
import com.gooplanycol.gooplany.application.ports.output.CreditCardOutputPort;
import com.gooplanycol.gooplany.domain.model.request.CreditCardRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService implements CreditCardInputPort {

    private final CreditCardOutputPort creditCardOutputPort;

    @Override
    public CreditCardResponse save(CreditCardRequest creditCard) {
        return creditCardOutputPort.save(creditCard);
    }

    @Override
    public CreditCardResponse edit(CreditCardRequest creditCard, Long id) {
        return creditCardOutputPort.edit(creditCard, id);
    }

    @Override
    public CreditCardResponse findById(Long id) {
        return creditCardOutputPort.findById(id);
    }

    @Override
    public CreditCardResponse findCardByNumber(String number) {
        return creditCardOutputPort.findCardByNumber(number);
    }

    @Override
    public List<CreditCardResponse> findAll(Integer offset, Integer pageSize) {
        return creditCardOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<CreditCardResponse> findCardsByType(Integer offset, Integer pageSize, String type) {
        return creditCardOutputPort.findCardsByType(offset, pageSize, type);
    }

    @Override
    public boolean remove(Long id) {
        return creditCardOutputPort.remove(id);
    }
}
