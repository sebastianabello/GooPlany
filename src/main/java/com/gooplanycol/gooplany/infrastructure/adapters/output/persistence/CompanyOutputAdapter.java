package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CompanyOutputPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.domain.model.request.CompanyRequestEdit;
import com.gooplanycol.gooplany.domain.model.response.AddressResponse;
import com.gooplanycol.gooplany.domain.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.domain.model.response.CompanyResponse;
import com.gooplanycol.gooplany.domain.model.response.HistoryResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Address;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.Token;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.AddressOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.HistoryOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.AddressRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.TokenRepository;
import com.gooplanycol.gooplany.utils.TokenType;
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
public class CompanyOutputAdapter implements CompanyOutputPort {

    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper companyOutputMapper;

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressOutputMapper;

    private final HistoryOutputMapper historyOutputMapper;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private void saveCompanyToken(Company company, String jwtToken) {
        Token token = new Token(
                null,
                jwtToken,
                TokenType.BEARER,
                company,
                false,
                false
        );
        tokenRepository.save(token);
    }

    private void revokeAllCompanyTokens(Company company) {
        List<Token> validCompanyTokens = tokenRepository.findAllValidTokenByCompany(company.getId());
        if (validCompanyTokens.isEmpty()) return;

        validCompanyTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validCompanyTokens);
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationCompany) {
        String username = authenticationCompany.username();
        String pwd = authenticationCompany.password();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, pwd
                )
        );
        Company company = companyRepository.findCompanyByUsername(username).orElse(null);
        String jwtToken = jwtService.generateToken(company);

        if (company != null) {
            revokeAllCompanyTokens(company);
        }
        saveCompanyToken(company, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public CompanyResponse getCompanyByToken(String token) {
        Company company = tokenRepository.getCompanyByToken(token);
        if (company != null) {
            return companyOutputMapper.toCompanyResponse(company);
        } else {
            throw new CompanyException("The company fetched by token doesn't exist");
        }
    }

    @Override
    public boolean removeCompany(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            if (company.getTokens() != null) {
                company.getTokens().forEach(token -> token.setCompany(null));
            }
            if (company.getConfirmationTokens() != null) {
                company.getConfirmationTokens().forEach(confirmationToken -> confirmationToken.setCompany(null));
            }
            company.setHistory(null);
            companyRepository.delete(company);
            return true;
        } else {
            throw new CompanyException("The company fetched to delete doesn't exist");
        }
    }

    @Override
    public CompanyResponse editData(CompanyRequestEdit companyEdit, Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            company.setName(companyEdit.name());
            company.setCellphone(companyEdit.cellphone());
            company.setEmail(companyEdit.email());
            company.setUsername(companyEdit.username());
            return companyOutputMapper.toCompanyResponse(companyRepository.save(company));
        } else {
            throw new CompanyException("The company fetched to update doesn't exist");
        }
    }

    @Override
    public CompanyResponse findById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            return companyOutputMapper.toCompanyResponse(company);
        } else {
            throw new CompanyException("The company fetched by id doesn't exist");
        }
    }

    @Override
    public List<CompanyResponse> findAll(Integer offset, Integer pageSize) {
        Page<Company> list = companyRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.getContent().stream().map(companyOutputMapper::toCompanyResponse).collect(Collectors.toList());
        } else {
            throw new CompanyException("The list of companies is null");
        }
    }

    @Override
    @Transactional
    public HistoryResponse findHistory(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null && company.getHistory() != null) {
            return historyOutputMapper.toHistoryResponse(company.getHistory());
        } else {
            throw new CompanyException("The company fetched to find history doesn't exist");
        }
    }

    @Override
    public List<AddressResponse> findAddress(Long id, Integer offset, Integer pageSize) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null && company.getAddress() != null) {
            Page<Address> list = companyRepository.findCompanyAddress(id, PageRequest.of(offset, pageSize));
            return list.stream().map(addressOutputMapper::toAddressResponse).collect(Collectors.toList());
        } else {
            throw new CompanyException("The company fetched to find address doesn't exist");
        }
    }

    @Override
    public CompanyResponse findByEmail(String email) {
        Company company = companyRepository.findCompanyByEmail(email).orElse(null);
        if (company != null) {
            return companyOutputMapper.toCompanyResponse(company);
        } else {
            throw new CompanyException("The company fetched by email doesn't exist");
        }
    }

    @Override
    public CompanyResponse changePwd(String pwd, Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            company.setPwd(passwordEncoder.encode(pwd));
            return companyOutputMapper.toCompanyResponse(companyRepository.save(company));
        } else {
            throw new CompanyException("The company fetched to change password doesn't exist");
        }
    }

    @Override
    @Transactional
    public List<AddressResponse> addAddress(AddressResponse addressResponse, Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null && addressResponse != null) {
            if (addressResponse.id() != null) {
                Address address = addressRepository.findById(addressResponse.id()).orElse(null);
                if (address != null) {
                    company.getAddress().add(address);
                } else {
                    throw new CompanyException("The address fetched to add doesn't exist");
                }
            } else {
                Address a = new Address(null, addressResponse.street(), addressResponse.country(), addressResponse.postalCode());
                company.getAddress().add(a);
            }
            companyRepository.save(company);
            Page<Address> list = companyRepository.findCompanyAddress(id, PageRequest.of(0, 10));
            return list.getContent().stream().map(addressOutputMapper::toAddressResponse).collect(Collectors.toList());
        } else {
            throw new CompanyException("The company fetched to add address doesn't exist");
        }
    }

    @Override
    public boolean removeAddress(Long addressId, Long companyId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null && address != null) {
            company.getAddress().remove(address);
            companyRepository.save(company);
            return true;
        } else {
            throw new CompanyException("The company fetched to remove address doesn't exist");
        }
    }
}
