package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CustomerOutputPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CustomerException;
import com.gooplanycol.gooplany.domain.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.domain.model.request.CustomerRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCard;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CreditCardRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenRepository;
import com.gooplanycol.gooplany.utils.TokenType;
import com.gooplanycol.gooplany.utils.TypeCard;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerOutputAdapter implements CustomerOutputPort {

    private final CustomerRepository customerRepository;
    private final CustomerOutputMapper customerOutputMapper;

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressMapper;

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutputMapper creditCardOutPutMapper;

    private final HistoryOutputMapper historyOutputMapper;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private void saveCustomerToken(Customer customer, String jwtToken) {
        Token token = new Token(
                null,
                jwtToken,
                TokenType.BEARER,
                customer,
                false,
                false
        );
        tokenRepository.save(token);
    }

    private void revokeAllCustomerTokens(Customer customer) {
        List<Token> validCustomerTokens = tokenRepository.findAllValidTokenByCustomer(customer.getId());
        if (validCustomerTokens.isEmpty())
            return;
        validCustomerTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validCustomerTokens);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.username();
        String pwd = authenticationRequest.password();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, pwd
                )
        );
        Customer customer = customerRepository.findCustomerByUsername(username).orElse(null);
        String jwtToken = jwtService.generateToken(customer);
        if (customer != null) {
            revokeAllCustomerTokens(customer);
        }
        saveCustomerToken(customer, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public CustomerResponse getCustomerByToken(String token) {
        Customer customer = tokenRepository.getCustomerByToken(token);
        if (customer != null) {
            return customerOutputMapper.toCustomerResponse(customer);
        } else {
            throw new CustomerException("The customer fetched by token doesn't exist");
        }
    }

    @Override
    public boolean removeCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            if (customer.getTokens() != null) {
                customer.getTokens().forEach(token -> token.setCustomer(null));
            }
            if (customer.getConfirmationTokens() != null) {
                customer.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCustomer(null));
            }
            customer.setHistory(null);
            customerRepository.delete(customer);
            return true;
        } else {
            throw new CustomerException("The customer fetched to delete doesn't exist");
        }
    }

    @Override
    public CustomerResponse editData(CustomerRequestEdit response, Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(response.name());
            customer.setLastName(response.lastName());
            customer.setCellphone(response.cellphone());
            customer.setEmail(response.email());
            return customerOutputMapper.toCustomerResponse(customerRepository.save(customer));
        } else {
            throw new CustomerException("The customer fetched to update doesn't exist");
        }
    }

    @Override
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomerResponse(customer);
        } else {
            throw new CustomerException("The customer fetched by id doesn't exist");
        }
    }

    @Override
    public List<CustomerResponse> findAll(Integer offset, Integer pageSize) {
        Page<Customer> list = customerRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.getContent().stream().map(customerOutputMapper::toCustomerResponse).collect(Collectors.toList());
        } else {
            throw new CustomerException("The list of customers is null");
        }
    }

    @Override
    @Transactional
    public HistoryResponse findHistory(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getHistory() != null) {
            return historyOutputMapper.toHistoryResponse(customer.getHistory());
        } else {
            throw new CustomerException("The customer's history doesn't exist");
        }
    }

    @Override
    public List<AddressResponse> findAddress(Long id, Integer offset, Integer pageSize) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getAddress() != null) {
            Page<Address> list = customerRepository.findCustomerAddress(id, PageRequest.of(offset, pageSize));
            return list.stream().map(addressMapper::toAddressResponse).collect(Collectors.toList());
        } else {
            throw new CustomerException("The list of customer's address is null");
        }
    }

    @Override
    @Transactional
    public List<CreditCardResponse> findCards(Long id, Integer offset, Integer pageSize) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getCards() != null) {
            Page<CreditCard> list = customerRepository.findCustomerCreditCards(id, PageRequest.of(offset, pageSize));
            return list.stream().map(creditCardOutPutMapper::toCreditCardResponse).collect(Collectors.toList());
        } else {
            throw new CustomerException("The list of customer's cards is null");
        }
    }

    @Override
    public CustomerResponse findByEmail(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomerResponse(customer);
        } else {
            throw new CustomerException("The customer fetched by email doesn't exist");
        }
    }

    @Override
    public CustomerResponse changePwd(String pwd, Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setPwd(passwordEncoder.encode(pwd));
            return customerOutputMapper.toCustomerResponse(customerRepository.save(customer));
        } else {
            throw new CustomerException("The customer fetched to change its pwd doesn't exist");
        }
    }

    @Override
    @Transactional
    public List<AddressResponse> addAddress(AddressResponse addressResponse, Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && addressResponse != null) {
            if (addressResponse.id() != null) {
                //the address exist
                Address addressFound = addressRepository.findById(addressResponse.id()).orElse(null);
                if (addressFound != null) {
                    customer.getAddress().add(addressFound);
                } else {
                    throw new CustomerException("The address to add doesn't exist");
                }
            } else {
                //the address doesn't exist
                Address a = new Address(null, addressResponse.street(), addressResponse.country(), addressResponse.postalCode());
                customer.getAddress().add(a);
            }
            customerRepository.save(customer);
            Page<Address> list = customerRepository.findCustomerAddress(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(addressMapper::toAddressResponse).collect(Collectors.toList());

        } else {
            throw new CustomerException("The customer or the address to add doesn't exist");
        }
    }

    @Override
    @Transactional
    public boolean removeAddress(Long addressId, Long customerId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null && address != null) {
            customer.getAddress().remove(address);
            customerRepository.save(customer);
            return true;
        } else {
            throw new CustomerException("The customer or the address to remove doesn't exist");
        }
    }

    @Override
    @Transactional
    public List<CreditCardResponse> addCreditCard(CreditCardResponse creditCardResponseDTO, Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && creditCardResponseDTO != null) {
            if (creditCardResponseDTO.id() != null) {
                //card exist
                CreditCard cardFound = creditCardRepository.findById(creditCardResponseDTO.id()).orElse(null);
                if (cardFound != null) {
                    customer.getCards().add(cardFound);
                } else {
                    throw new CustomerException("The card to add doesn't exist");
                }
            } else {
                //card doesn't exist
                CreditCard newCard = new CreditCard(null, creditCardResponseDTO.number(), typeCard(creditCardResponseDTO.type()));
                customer.getCards().add(newCard);
            }
            customerRepository.save(customer);
            Page<CreditCard> list = customerRepository.findCustomerCreditCards(customer.getId(), PageRequest.of(0, 10));
            return list.stream().map(creditCardOutPutMapper::toCreditCardResponse).collect(Collectors.toList());
        } else {
            throw new CustomerException("The customer or the card to add doesn't exist");
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
    @Transactional
    public boolean removeCreditCard(Long creditCardId, Long customerId) {
        CreditCard card = creditCardRepository.findById(creditCardId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null && card != null) {
            customer.getCards().remove(card);
            customerRepository.save(customer);
            return true;
        } else {
            throw new CustomerException("The customer or the card to remove doesn't exist");
        }
    }

}
