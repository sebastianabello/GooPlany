package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CustomerOutputPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.exception.CustomerException;
import com.gooplanycol.gooplany.domain.model.Authentication;
import com.gooplanycol.gooplany.domain.model.CreditCard;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.domain.model.HistoryCustomer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CreditCardEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.CustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.TokenEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CreditCardOutPutMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryCustomerOutputMapper;
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

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutPutMapper creditCardOutPutMapper;

    private final HistoryCustomerOutputMapper historyCustomerOutputMapper;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private void saveProfileToken(CustomerEntity customer, String jwtToken) {
        TokenEntity token = new TokenEntity(
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
        List<TokenEntity> validProfileTokens = tokenRepository.findAllValidTokenByCustomer(customer.getId());
        if (validProfileTokens.isEmpty())
            return;
        validProfileTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validProfileTokens);
    }


    @Override
    public Authentication authenticate(Customer authenticationCustomer) {
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
            return new Authentication(jwtToken);
        } else {
            throw new CompanyException("The customer fetched by username doesn't exist");
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
                customer.getTokens().forEach(token -> token.setCompany(null));
            }
            if (customer.getConfirmationTokens() != null) {
                customer.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCustomer(null));
            }
            customer.setHistoryCustomer(null);
            customer.setAddress(null);
            customerRepository.delete(customer);
            return true;
        } else {
            throw new CompanyException("The customer fetched to delete doesn't exist");
        }
    }

    @Override
    public Customer editData(Customer customerEdit, Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setLastName(customerEdit.getLastName());
            customer.setEnable(customerEdit.isEnable());
            customer.setUsername(customerEdit.getUsername());
            customer.setPwd(customerEdit.getPwd());
            customer.setBirthdate(customerEdit.getBirthdate());
            customer.setCountry(customerEdit.getCountry());
            customer.setDescription(customerEdit.getDescription());
            customer.setEmergencyContact(customerEdit.getEmergencyContact());
            customer.setGender(customerEdit.getGender());
            customer.setLevel(customerEdit.getLevel());
            customer.setRoles(customerEdit.getRoles());
            return customerOutputMapper.toCustomer(customerRepository.save(customer));
        } else {
            throw new CompanyException("The customer fetched to edit its data doesn't exist");
        }
    }

    @Override
    public Customer findById(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return customerOutputMapper.toCustomer(customer);
        } else {
            throw new CompanyException("The profile fetched by id doesn't exist");
        }
    }

    @Override
    public List<Customer> findAll(Integer offset, Integer pageSize) {
        Page<CustomerEntity> list = customerRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.getContent().stream().map(customerOutputMapper::toCustomer).toList();
        } else {
            throw new CompanyException("The list of companies is null");
        }
    }

    @Override
    @Transactional
    public HistoryCustomer findHistory(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getHistoryCustomer() != null) {
            return historyCustomerOutputMapper.toHistoryCustomer(customer.getHistoryCustomer());
        } else {
            throw new CustomerException("The customer's history doesn't exist");
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
            throw new CompanyException("The company fetched by email doesn't exist");
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
                        .holderName(creditCard.getHolderName())
                        .number(creditCard.getNumber())
                        .monthExp(creditCard.getMonthExp())
                        .yearExp(creditCard.getYearExp())
                        .cvv(creditCard.getCvv())
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
