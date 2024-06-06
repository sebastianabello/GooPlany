package com.gooplanycol.gooplany.service;

import com.gooplanycol.gooplany.application.ports.output.EmailInputPort;
import com.gooplanycol.gooplany.application.service.EmailValidator;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.CustomerResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.CustomerOutputAdapter;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.registration.ConfirmationTokenOutputAdapter;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.registration.RegistrationOutputAdapter;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CreditCardRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerOutputAdapterTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private CustomerOutputMapper customerDTOMapper;
    @Mock
    private HistoryOutputMapper historyDTOMapper;
    @Mock
    private AddressOutputMapper addressDTOMapper;
    @Mock
    private CreditCardOutputMapper creditCardDTOMapper;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailInputPort emailSender;
    @Mock
    private ConfirmationTokenOutputAdapter confirmationTokenService;
    @InjectMocks
    private CustomerOutputAdapter customerServiceImp;
    @InjectMocks
    private RegistrationOutputAdapter registrationServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);}

    @AfterEach
    void tearDown() {
    }

    @Test
    void remove() {
        // Arrange
        Long id = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(id)).thenReturn(Optional.ofNullable(customer));
        // Act
        boolean result = customerServiceImp.removeCustomer(id);

        // Assertion
        Assertions.assertThat(result).isTrue();
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void editData() {
        //Arrange
        CustomerRequestEdit customerRequestEditDTO = new CustomerRequestEdit(
                "new peter",
                "new bing",
                "343532",
                "peter@gmail.com",
                "newPeter"
        );
        Customer customerSaved = new Customer();
        customerSaved.setId(4L);
        customerSaved.setName("peter");
        customerSaved.setLastName("bing");
        customerSaved.setCellphone("89262293");
        customerSaved.setEmail("peter@gmail.com");
        customerSaved.setUsername("peter");
        customerSaved.setPwd("peter123");
        Customer customerEdited = new Customer();
        customerEdited.setId(4L);
        customerEdited.setName("new peter");
        customerEdited.setLastName("new bing");
        customerEdited.setCellphone("343532");
        customerEdited.setEmail("peter@gmail.com");
        customerEdited.setUsername("newPeter");
        customerEdited.setPwd("peter123");
        CustomerResponse expectedCustomerResponseDTO = new CustomerResponse(
                4L,
                "new peter",
                "new bing",
                "343532",
                "peter@gmail.com",
                "newPeter"
        );

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customerSaved));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerEdited);
        when(customerDTOMapper.toCustomerResponse(customerEdited)).thenReturn(expectedCustomerResponseDTO);

        //Act
        Optional<Customer> optionalCustomer = customerRepository.findById(1L);
        if (!optionalCustomer.isPresent()) {
            throw new RuntimeException("Customer not found");
        }
        CustomerResponse customerResponseDTO = customerServiceImp.editData(customerRequestEditDTO,1L);

        //Assertion
        Assertions.assertThat(customerResponseDTO).isEqualTo(expectedCustomerResponseDTO);
    }


}
