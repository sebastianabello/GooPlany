package com.gooplanycol.gooplany.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenRepository;
import com.gooplanycol.gooplany.utils.Role;
import com.gooplanycol.gooplany.utils.TokenType;
import com.gooplanycol.gooplany.utils.TypeCard;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeAll
    void setUp() {

        History history = History.builder()
                .eventsFinished(new ArrayList<>())
                .dateModification(LocalDate.now())
                .build();
        Address address = new Address(null,"spain avenue","Spain","28903");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        CreditCard creditCard1 = new CreditCard(null,"167439276482", TypeCard.MASTER_CARD);
        CreditCard creditCard2 = new CreditCard(null,"1103839276482", TypeCard.VISA);
        List<CreditCard> cards = new ArrayList<>();
        cards.add(creditCard1);
        cards.add(creditCard2);

        Customer customer = new Customer(
                history,
                addresses,
                cards,
                new ArrayList<Token>(),
                new ArrayList<ConfirmationToken>(),
                true,
                "peter",
                "peter123",
                Arrays.asList(Role.CUSTOMER));
        customer.setName("peter");
        customer.setLastName("bing");
        customer.setCellphone("89262293");
        customer.setEmail("peter@gmail.com");
        customer = customerRepository.save(customer);

        Token token1 = new Token(null,"HDHJSGWUE-28746734-jsduf", TokenType.BEARER,customer,false,false);
        Token token2 = new Token(null,"HDHJSGWUE-28712334-jsduf", TokenType.BEARER,customer,false,false);
        Token token3 = new Token(null,"HDHJSGWUE-28700004-jsduf", TokenType.BEARER,customer,false,false);
        tokenRepository.save(token1);
        tokenRepository.save(token2);
        tokenRepository.save(token3);
    }

    @AfterAll
    void tearDown() {
    }

    @Test
    void findAllValidTokenByCustomer() {
        List<Token> tokens = tokenRepository.findAllValidTokenByCustomer(1L);
        assertEquals(tokens.size(),3);
    }

    @Test
    void findTokenByToken() {
        Token token1 = tokenRepository.findTokenByToken("HDHJSGWUE-28700004-jsduf").orElse(null);
        assertTrue(token1!=null);
        Token token2 = tokenRepository.findTokenByToken("HDHJSGWUE-00000004-jsduf").orElse(null);
        assertFalse(token2!=null);
    }
}
