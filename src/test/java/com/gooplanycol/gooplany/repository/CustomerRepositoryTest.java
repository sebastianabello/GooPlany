package com.gooplanycol.gooplany.repository;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.utils.Role;
import com.gooplanycol.gooplany.utils.TypeCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeAll
    void setUp() {
        History history = History.builder()
                .eventsFinished(new ArrayList<>())
                .dateModification(LocalDate.now())
                .build();
        Address address = new Address(null, "avenida dorado", "bogota", "28903");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        CreditCard creditCard1 = new CreditCard(null, "167439276482", TypeCard.MASTER_CARD);
        CreditCard creditCard2 = new CreditCard(null, "1103839276482", TypeCard.VISA);
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
                "sebastian1",
                "sebastian123",
                Arrays.asList(Role.CUSTOMER)
        );
        customer.setName("sebastian");
        customer.setLastName("abello");
        customer.setCellphone("89262293");
        customer.setEmail("sebastian@gmail.com");
        customerRepository.save(customer);
    }

    @Test
    void findCustomerHistory() {
        assertTrue(customerRepository.findCustomerHistory(1L).isPresent());
    }

    @Test
    void findCustomerAddress() {
        org.assertj.core.api.Assertions.assertThat(customerRepository.findCustomerAddress(1L, PageRequest.of(0, 10))).isNotNull();
    }

    @Test
    void findCustomerCreditCards() {
        assertTrue(!customerRepository.findCustomerCreditCards(1L, PageRequest.of(0, 10)).isEmpty());
        assertTrue(customerRepository.findCustomerCreditCards(4L, PageRequest.of(0, 10)).isEmpty());
    }

    @Test
    void findCustomerByEmail() {
        assertTrue(customerRepository.findCustomerByEmail("sebastian@gmail.com").isPresent());
        assertFalse(customerRepository.findCustomerByEmail("seb@gmail.com").isPresent());
    }

    @Test
    void findCustomerById() {
        assertTrue(customerRepository.findById(1L).isPresent());
        assertFalse(customerRepository.findById(2L).isPresent());
    }
}
