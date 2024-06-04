package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CustomerOutputPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CustomerException;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutPutMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CreditCardRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenCustomerRepository;
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
    private final CreditCardOutPutMapper creditCardOutPutMapper;

    private final HistoryOutputMapper historyOutputMapper;

    private final TokenCustomerRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private void saveProfileToken(CustomerEntity customer, String jwtToken) {
        TokenCustomerEntity token = new TokenCustomerEntity(
                null,
                jwtToken,
                TokenType.BEARER,
                customer,
                false,
                false
        );
        tokenRepository.save(token);
    }

    private void revokeAllProfileTokens(CustomerEntity customer) {
        List<TokenCustomerEntity> validProfileTokens = tokenRepository.findAllValidTokenByCustomer(customer.getId());
        if (validProfileTokens.isEmpty())
            return;
        validProfileTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validProfileTokens);
    }


    @Override
    public Customer authenticate(Customer authenticationCustomer) {
        String username = authenticationCustomer.getUsername();
        String pwd = authenticationCustomer.getPwd();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, pwd
                )
        );
        CustomerEntity customer = customerRepository.findCustomerByUsername(username).orElse(null);
        String jwtToken = jwtService.generateToken(customer);
        if (customer != null) {
            revokeAllProfileTokens(customer);
            saveProfileToken(customer, jwtToken);
            return new Customer(jwtToken);
        } else {
            throw new CustomerException("The customer fetched by username doesn't exist");
        }

    }

    @Override
    public Customer getCustomerByToken(String token) {
        CustomerEntity customer = tokenRepository.getCustomerByToken(token);
        if (customer != null) {
            return customerOutputMapper.toCustomer(customer);
        } else {
            throw new CustomerException("The customer fetched by token doesn't exist");
        }
    }

    @Override
    public boolean removeCustomer(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            if (customer.getTokens() != null) {
                customer.getTokens().forEach(token -> token.setCustomer(null));
            }
            if (customer.getConfirmationTokens() != null) {
                customer.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCustomer(null));
            }
            customer.setHistory(null);
            customer.setAddress(null);
            customerRepository.delete(customer);
            return true;
        } else {
            throw new CustomerException("The customer fetched to delete doesn't exist");
        }
    }

    @Override
    public Customer editData(Customer customerEdit, Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(customerEdit.getName());
            customer.setLastName(customerEdit.getLastName());
            customer.setCellphone(customerEdit.getCellphone());
            customer.setEmail(customerEdit.getEmail());
            customer.setDescription(customerEdit.getDescription());
            customer.setEmergencyContact(customerEdit.getEmergencyContact());
            customer.setGender(customerEdit.findGender(customerEdit.getGender().name()));
            customer.setLevel(customerEdit.findLevel(customerEdit.getLevel().name()));
            return customerOutputMapper.toCustomer(customerRepository.save(customer));
        } else {
            throw new CustomerException("The customer fetched to edit its data doesn't exist");
        }
    }

    @Override
    public Customer findById(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomer(customer);
        } else {
            throw new CustomerException("The profile fetched by id doesn't exist");
        }
    }

    @Override
    public List<Customer> findAll(Integer offset, Integer pageSize) {
        Page<CustomerEntity> list = customerRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.getContent().stream().map(customerOutputMapper::toCustomer).toList();
        } else {
            throw new CustomerException("The list of companies is null");
        }
    }

    @Override
    @Transactional
    public History findHistory(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getHistory() != null) {
            return historyOutputMapper.toHistory(customer.getHistory());
        } else {
            throw new CustomerException("The customer's history doesn't exist");
        }
    }

    @Override
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getAddress() != null) {
            Page<AddressEntity> list = customerRepository.findCustomerAddresses(id, PageRequest.of(offset, pageSize));
            return list.stream().map(addressMapper::toAddress).collect(Collectors.toList());
        } else {
            throw new CustomerException("The list of customer's addresses is null");
        }
    }

    @Override
    @Transactional
    public List<CreditCard> findCards(Long id, Integer offset, Integer pageSize) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getCards() != null) {
            Page<CreditCardEntity> list = customerRepository.findCustomerCreditCards(id, PageRequest.of(offset, pageSize));
            return list.stream().map(creditCardOutPutMapper::toCreditCard).collect(Collectors.toList());
        } else {
            throw new CustomerException("The list of customer's cards is null");
        }
    }

    @Override
    public Customer findByEmail(String email) {
        CustomerEntity customer = customerRepository.findCustomerByEmail(email).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomer(customer);
        } else {
            throw new CustomerException("The customer fetched by email doesn't exist");
        }
    }

    @Override
    public Customer changePwd(String pwd, Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setPwd(passwordEncoder.encode(pwd));
            return customerOutputMapper.toCustomer(customerRepository.save(customer));
        } else {
            throw new CustomerException("The customer fetched to change its pwd doesn't exist");
        }
    }

    @Override
    @Transactional
    public List<Address> addAddress(Address addressRequest, Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null && addressRequest != null) {
            if (addressRequest.getId() != null) {
                //the address exist
                AddressEntity addressFound = addressRepository.findById(addressRequest.getId()).orElse(null);
                if (addressFound != null) {
                    customer.getAddress().add(addressFound);
                } else {
                    throw new CustomerException("The address to add doesn't exist");
                }
            } else {
                //the address doesn't exist
                AddressEntity a = new AddressEntity(null, addressRequest.getStreet(), addressRequest.getCountry(), addressRequest.getPostalCode());
                customer.getAddress().add(a);
            }
            customerRepository.save(customer);
            Page<AddressEntity> list = customerRepository.findCustomerAddresses(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(addressMapper::toAddress).collect(Collectors.toList());
        } else {
            throw new CustomerException("The customer or the address to add doesn't exist");
        }
    }

    @Override
    @Transactional
    public boolean removeAddress(Long addressId, Long customerId) {
        AddressEntity address = addressRepository.findById(addressId).orElse(null);
        CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
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
    public List<CreditCard> addCreditCard(CreditCard creditCard, Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null && creditCard != null) {
            if (creditCard.getId() != null) {
                //card exist
                CreditCardEntity cardFound = creditCardRepository.findById(creditCard.getId()).orElse(null);
                if (cardFound != null) {
                    customer.getCards().add(cardFound);
                } else {
                    throw new CustomerException("The card to add doesn't exist");
                }
            } else {

                CreditCardEntity newCard = CreditCardEntity.builder()
                        .number(creditCard.getNumber())
                        .typeCard(typeCard(creditCard.getTypeCard().name()))
                        .build();
                customer.getCards().add(newCard);
            }
            customerRepository.save(customer);
            Page<CreditCardEntity> list = customerRepository.findCustomerCreditCards(customer.getId(), PageRequest.of(0, 10));
            return list.stream().map(creditCardOutPutMapper::toCreditCard).collect(Collectors.toList());
        } else {
            throw new CustomerException("The customer or the card to add doesn't exist");
        }
    }

    private TypeCard typeCard(String type){
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
        CreditCardEntity card = creditCardRepository.findById(creditCardId).orElse(null);
        CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
        if(customer!=null && card!=null){
            customer.getCards().remove(card);
            customerRepository.save(customer);
            return true;
        }else{
            throw new CustomerException("The customer or the card to remove doesn't exist");
        }
    }

}
