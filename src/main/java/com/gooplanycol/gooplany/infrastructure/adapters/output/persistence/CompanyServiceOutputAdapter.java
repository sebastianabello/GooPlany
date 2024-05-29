package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CompanyOutPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.model.Address;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.History;
import com.gooplanycol.gooplany.domain.model.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.TokenOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenRepository;
import com.gooplanycol.gooplany.utils.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyServiceOutputAdapter implements CompanyOutPort {

    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper companyOutputMapper;

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressOutputMapper;

    private final TokenRepository tokenRepository;
    private final TokenOutputMapper tokenOutputMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    // private final ConfirmationTokenOutputAdapter confirmationTokenOutputAdapter;

    private final PasswordEncoder passwordEncoder;

    private void saveCustomerToken(Company company, String jwtToken) {
        Token token = new Token(
                null,
                jwtToken,
                TokenType.BEARER,
                company,
                false,
                false
        );
        tokenOutputMapper.toToken(tokenRepository.save(tokenOutputMapper.toTokenEntity(token)));
    }

    private void revokeAllCustomerTokens(Company company) {
        List<Token> validCustomerTokens = tokenOutputMapper.toTokenList(tokenRepository.findAllValidTokenByCompany(company.getId()));
        if (validCustomerTokens.isEmpty())
            return;
        validCustomerTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenOutputMapper.toTokenList(tokenRepository.saveAll(tokenOutputMapper.toTokenEntityList(validCustomerTokens)));
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO) {
        String username = authenticationRequestDTO.username();
        String pwd = authenticationRequestDTO.password();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, pwd
                )
        );
        Company company = companyOutputMapper.toCompany(companyRepository.findCompanyByName(username).orElse(null));
        String jwtToken = jwtService.generateToken(companyOutputMapper.toCompanyEntity(company));
        revokeAllCustomerTokens(company);
        saveCustomerToken(company, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public Company save(Company company) {
        return companyOutputMapper.toCompany(companyRepository.save(companyOutputMapper.toCompanyEntity(company)));
    }

    @Override
    public Company getCustomerByToken(String token) {
        Company company = companyOutputMapper.toCompany(tokenRepository.getCompanyByToken(token));
        if (company != null) {
            return company;
        } else {
            throw new CompanyException("The company fetched by token doesn't exist");
        }
    }

    @Override
    public boolean removeCompany(Long id) {
        Company company = companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
        if (company != null) {
            if (company.getTokens() != null) {
                company.getTokens().forEach(token -> token.setCompany(null));
            }
            if (company.getConfirmationTokens() != null) {
                company.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCompany(null));
            }
            company.setHistory(null);
            companyRepository.delete(companyOutputMapper.toCompanyEntity(company));
            return true;
        } else {
            throw new CompanyException("The company fetched to delete doesn't exist");
        }
    }

    @Override
    public Optional<Company> findById(Long id) {
        Company company = companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
        if (company != null) {
            return companyOutputMapper.toCompanyOptional(company);
        } else {
            throw new CompanyException("The company fetched by id doesn't exist");
        }
    }

    @Override
    public List<Company> findAll(Integer offset, Integer pageSize) {
        Page<Company> list = companyOutputMapper.toCompanyPage(companyRepository.findAll(PageRequest.of(offset, pageSize)));
        if (list != null && !list.isEmpty()) {
            return new ArrayList<>(list.getContent());
        } else {
            throw new CompanyException("The list of companies is null");
        }
    }

    @Override
    public History findHistory(Long id) {
        Company company = companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
        if (company != null && company.getHistory() != null) {
            return company.getHistory();
        } else {
            throw new CompanyException("The company's history doesn't exist");
        }
    }

    @Override
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        Company company = companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
        if (company != null && company.getAddress() != null) {
            Page<Address> list = addressOutputMapper.toAddressPage(companyRepository.findCompanyByAddress(id, PageRequest.of(offset, pageSize)));
            return list.stream().collect(Collectors.toList());
        } else {
            throw new CompanyException("The list of company's address is null");
        }
    }

    @Override
    public Company findByEmail(String email) {
        Company company = companyOutputMapper.toCompany(companyRepository.findCompanyByEmail(email).orElse(null));
        if (company != null) {
            return company;
        } else {
            throw new CompanyException("The company fetched by email doesn't exist");
        }
    }

    @Override
    public Company changePwd(String pwd, Long id) {
        Company company = companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
        if (company != null) {
            company.setPwd(passwordEncoder.encode(pwd));
            return companyOutputMapper.toCompany(companyRepository.save(companyOutputMapper.toCompanyEntity(company)));
        } else {
            throw new CompanyException("The company fetched to change its pwd doesn't exist");
        }
    }

    @Override
    public List<Address> addAddress(Address address, Long id) {
        Company company = companyOutputMapper.toCompany(companyRepository.findById(id).orElse(null));
        if (company != null && address != null) {
            if (address.getId() != null) {
                //the address exist
                Address addressFound = addressOutputMapper.toAddress(addressRepository.findById(address.getId()).orElse(null));
                if (addressFound != null) {
                    company.getAddress().add(addressFound);
                } else {
                    throw new CompanyException("The address to add doesn't exist");
                }
            } else {
                //the address doesn't exist
                Address a = new Address(null, address.getStreet(), address.getCountry(), address.getPostalCode());
                company.getAddress().add(a);
            }
            companyRepository.save(companyOutputMapper.toCompanyEntity(company));
            Page<Address> list = addressOutputMapper.toAddressPage(companyRepository.findCompanyByAddress(id, PageRequest.of(0, 10)));
            return new ArrayList<>(list.getContent());

        } else {
            throw new CompanyException("The company or the address to add doesn't exist");
        }
    }

    @Override
    public boolean removeAddress(Long addressId, Long companyId) {
        Address address = addressOutputMapper.toAddress(addressRepository.findById(addressId).orElse(null));
        Company company = companyOutputMapper.toCompany(companyRepository.findById(companyId).orElse(null));
        if (company != null && address != null) {
            company.getAddress().remove(address);
            companyRepository.save(companyOutputMapper.toCompanyEntity(company));
            return true;
        } else {
            throw new CompanyException("The company or the address to remove doesn't exist");
        }
    }
}
