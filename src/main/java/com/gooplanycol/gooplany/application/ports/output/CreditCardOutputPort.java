package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.request.CreditCardRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;

import java.util.List;

public interface CreditCardOutputPort {

    CreditCardResponse save(CreditCardRequest creditCard);

    CreditCardResponse edit(CreditCardRequest creditCard, Long id);

    boolean remove(Long id);

    CreditCardResponse findById(Long id);

    List<CreditCardResponse> findAll(Integer offset, Integer pageSize);

    CreditCardResponse findCardByNumber(String number);

    List<CreditCardResponse> findCardsByType(Integer offset, Integer pageSize, String type);

}
