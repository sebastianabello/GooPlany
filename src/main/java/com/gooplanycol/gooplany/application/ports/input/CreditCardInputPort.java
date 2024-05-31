package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.CreditCard;

import java.util.List;

public interface CreditCardInputPort {

     CreditCard save(CreditCard creditCard);
     CreditCard edit(CreditCard creditCard, Long id);
     CreditCard findById(Long id);
     CreditCard findCardByNumber(String number);
     List<CreditCard> findAll(Integer offset, Integer pageSize);
     List<CreditCard> findCardsByType(Integer offset, Integer pageSize,String type);
     boolean remove(Long id);

}
