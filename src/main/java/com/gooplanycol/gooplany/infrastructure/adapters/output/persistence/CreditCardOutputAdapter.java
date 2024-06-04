package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CreditCardOutputPort;
import com.gooplanycol.gooplany.domain.exception.CreditCardException;
import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCardEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutPutMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CreditCardRepository;
import com.gooplanycol.gooplany.utils.TypeCard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreditCardOutputAdapter implements CreditCardOutputPort {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutPutMapper creditCardOutPutMapper;

    public TypeCard typeCard(String type) {
        return switch (type) {
            case "VISA" -> TypeCard.VISA;
            case "MASTER_CARD" -> TypeCard.MASTER_CARD;
            case "AMERICAN_EXPRESS" -> TypeCard.AMERICAN_EXPRESS;
            default -> null;
        };
    }

    @Override
    public CreditCard save(CreditCard creditCard) {
        if (creditCard != null) {
            CreditCardEntity creditCardEntity = CreditCardEntity.builder()
                    .number(creditCard.getNumber())
                    .typeCard(typeCard(creditCard.getTypeCard().name()))
                    .build();
            return creditCardOutPutMapper.toCreditCard(creditCardRepository.save(creditCardEntity));
        } else {
            throw new CreditCardException("The credit card to save is null");
        }
    }

    @Override
    public CreditCard edit(CreditCard creditCard, Long id) {
        CreditCardEntity card = creditCardRepository.findById(id).orElse(null);
        if (card != null && creditCard != null) {
            card.setNumber(creditCard.getNumber());
            card.setTypeCard(typeCard(creditCard.getTypeCard().name()));
            return creditCardOutPutMapper.toCreditCard(creditCardRepository.save(card));
        } else {
            throw new CreditCardException("The card to update doesn't exist or the request is null");
        }
    }

    @Override
    public CreditCard findById(Long id) {
        CreditCardEntity card = creditCardRepository.findById(id).orElse(null);
        if (card != null) {
            return creditCardOutPutMapper.toCreditCard(card);
        } else {
            throw new CreditCardException("The card fetched by id doesn't exist");
        }
    }

    @Override
    public CreditCard findCardByNumber(String number) {
        CreditCardEntity card = creditCardRepository.findCreditCardByNumber(number).orElse(null);
        if (card != null) {
            return creditCardOutPutMapper.toCreditCard(card);
        } else {
            throw new CreditCardException("The credit fetched by number doesn't exist");
        }
    }

    @Override
    public List<CreditCard> findAll(Integer offset, Integer pageSize) {
        Page<CreditCardEntity> cards = creditCardRepository.findAll(PageRequest.of(offset, pageSize));
        if (!cards.isEmpty()) {
            return cards.getContent().stream().map(creditCardOutPutMapper::toCreditCard).collect(Collectors.toList());
        } else {
            throw new CreditCardException("The list of card is null");
        }
    }

    @Override
    public List<CreditCard> findCardsByType(Integer offset, Integer pageSize, String type) {
        Page<CreditCardEntity> cards = creditCardRepository.findCreditCardsByTypeCard(PageRequest.of(offset, pageSize), typeCard(type));
        if (cards != null) {
            return cards.getContent().stream().map(creditCardOutPutMapper::toCreditCard).collect(Collectors.toList());
        } else {
            throw new CreditCardException("The list of card is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if (creditCardRepository.existsById(id)) {
            creditCardRepository.deleteById(id);
            return true;
        } else {
            throw new CreditCardException("The card fetched to delete doesn't exist");
        }
    }
}
