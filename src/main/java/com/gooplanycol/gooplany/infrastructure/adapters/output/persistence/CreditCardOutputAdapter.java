package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CreditCardOutputPort;
import com.gooplanycol.gooplany.domain.exception.CreditCardException;
import com.gooplanycol.gooplany.domain.model.request.CreditCardRequest;
import com.gooplanycol.gooplany.domain.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutputMapper;
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
    private final CreditCardOutputMapper creditCardOutPutMapper;

    @Override
    public CreditCardResponse save(CreditCardRequest request) {
        if (request != null) {
            CreditCard creditCard = CreditCard.builder()
                    .number(request.number())
                    .typeCard(typeCard(request.typeCard()))
                    .build();
            return creditCardOutPutMapper.toCreditCardResponse(creditCardRepository.save(creditCard));
        } else {
            throw new CreditCardException("The credit card to save is null");
        }
    }

    private TypeCard typeCard(String type) {
        return switch (type) {
            case "VISA" -> TypeCard.VISA;
            case "MASTER_CARD" -> TypeCard.MASTER_CARD;
            case "AMERICAN_EXPRESS" -> TypeCard.AMERICAN_EXPRESS;
            default -> null;
        };
    }

    @Override
    public CreditCardResponse edit(CreditCardRequest request, Long id) {
        CreditCard card = creditCardRepository.findById(id).orElse(null);
        if (card != null && request != null) {
            card.setNumber(request.number());
            card.setTypeCard(typeCard(request.typeCard()));
            return creditCardOutPutMapper.toCreditCardResponse(creditCardRepository.save(card));
        } else {
            throw new CreditCardException("The card to update doesn't exist or the request is null");
        }
    }

    @Override
    public CreditCardResponse findById(Long id) {
        CreditCard card = creditCardRepository.findById(id).orElse(null);
        if (card != null) {
            return creditCardOutPutMapper.toCreditCardResponse(card);
        } else {
            throw new CreditCardException("The card fetched by id doesn't exist");
        }
    }

    @Override
    public CreditCardResponse findCardByNumber(String number) {
        CreditCard card = creditCardRepository.findCreditCardByNumber(number).orElse(null);
        if (card != null) {
            return creditCardOutPutMapper.toCreditCardResponse(card);
        } else {
            throw new CreditCardException("The credit fetched by number doesn't exist");
        }
    }

    @Override
    public List<CreditCardResponse> findAll(Integer offset, Integer pageSize) {
        Page<CreditCard> cards = creditCardRepository.findAll(PageRequest.of(offset, pageSize));
        if (cards != null && !cards.isEmpty()) {
            return cards.getContent().stream().map(creditCard -> {
                return creditCardOutPutMapper.toCreditCardResponse(creditCard);
            }).collect(Collectors.toList());
        } else {
            throw new CreditCardException("The list of card is null");
        }
    }

    @Override
    public List<CreditCardResponse> findCardsByType(Integer offset, Integer pageSize, String type) {
        Page<CreditCard> cards = creditCardRepository.findCreditCardsByTypeCard(PageRequest.of(offset, pageSize), typeCard(type));
        if (cards != null) {
            return cards.getContent().stream().map(creditCard -> {
                return creditCardOutPutMapper.toCreditCardResponse(creditCard);
            }).collect(Collectors.toList());
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
