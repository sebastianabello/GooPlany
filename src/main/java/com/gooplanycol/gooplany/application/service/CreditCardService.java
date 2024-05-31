package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CreditCardInputPort;
import com.gooplanycol.gooplany.application.ports.output.CreditCardOutputPort;
import com.gooplanycol.gooplany.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService implements CreditCardInputPort {

    private final CreditCardOutputPort creditCardOutputPort;

    @Override
    public CreditCard save(CreditCard creditCard) {
        return creditCardOutputPort.save(creditCard);
    }

    @Override
    public CreditCard edit(CreditCard creditCard, Long id) {
        return creditCardOutputPort.edit(creditCard, id);
    }

    @Override
    public CreditCard findById(Long id) {
        return creditCardOutputPort.findById(id);
    }

    @Override
    public CreditCard findCardByNumber(String number) {
        return creditCardOutputPort.findCardByNumber(number);
    }

    @Override
    public List<CreditCard> findAll(Integer offset, Integer pageSize) {
        return creditCardOutputPort.findAll(offset, pageSize);
    }

    @Override
    public List<CreditCard> findCardsByType(Integer offset, Integer pageSize, String type) {
        return creditCardOutputPort.findCardsByType(offset, pageSize, type);
    }

    @Override
    public boolean remove(Long id) {
        return creditCardOutputPort.remove(id);
    }
}
