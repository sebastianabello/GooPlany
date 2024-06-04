package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CompanyOutPort;
import com.gooplanycol.gooplany.application.service.JwtService;
import com.gooplanycol.gooplany.domain.exception.CompanyException;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.*;
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
public class CompanyOutputAdapter implements CompanyOutPort {

    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper companyOutputMapper;

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressOutputMapper;

    private final HistoryOutputMapper historyOutputMapper;

    private final TokenCompanyRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private void saveCompanyToken(CompanyEntity company, String jwtToken) {
        TokenCompanyEntity token = new TokenCompanyEntity(
                null,
                jwtToken,
                TokenType.BEARER,
                company,
                false,
                false
        );
        tokenRepository.save(token);
    }

    private void revokeAllCompanyTokens(CompanyEntity company) {
        List<TokenCompanyEntity> validCompanyTokens = tokenRepository.findAllValidTokenByCompany(company.getId());
        if (validCompanyTokens.isEmpty())
            return;
        validCompanyTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validCompanyTokens);
    }


    @Override
    public Company authenticate(Company authenticationCompany) {
        String username = authenticationCompany.getUsername();
        String pwd = authenticationCompany.getPwd();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, pwd
                )
        );
        CompanyEntity company = companyRepository.findCompanyByUsername(username).orElse(null);
        String jwtToken = jwtService.generateToken(company);
        if (company != null) {
            revokeAllCompanyTokens(company);
            saveCompanyToken(company, jwtToken);
            return new Company(jwtToken);
        } else {
            throw new CompanyException("The company fetched to authenticate doesn't exist");
        }
    }

    @Override
    public Company getCompanyByToken(String token) {
        CompanyEntity company = tokenRepository.getCompanyByToken(token);
        if (company != null) {
            return companyOutputMapper.toCompany(company);
        } else {
            throw new CompanyException("The company fetched by token doesn't exist");
        }
    }

    @Override
    public boolean removeCompany(Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
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
    public Company editData(Company companyEdit, Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            company.setName(companyEdit.getName());
            company.setCellphone(companyEdit.getCellphone());
            company.setNit(companyEdit.getNit());
            company.setEmail(companyEdit.getEmail());
            return companyOutputMapper.toCompany(companyRepository.save(company));
        } else {
            throw new CompanyException("The company fetched to update doesn't exist");
        }
    }

    @Override
    public Company findById(Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            return companyOutputMapper.toCompany(company);
        } else {
            throw new CompanyException("The company fetched by id doesn't exist");
        }
    }

    @Override
    public List<Company> findAll(Integer offset, Integer pageSize) {
        Page<CompanyEntity> list = companyRepository.findAll(PageRequest.of(offset, pageSize));
        if (!list.isEmpty()) {
            return list.getContent().stream().map(companyOutputMapper::toCompany).collect(Collectors.toList());
        } else {
            throw new CompanyException("The list of company is null");
        }
    }

    @Override
    @Transactional
    public History findHistory(Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        if (company != null && company.getHistory() != null) {
            return historyOutputMapper.toHistory(company.getHistory());
        } else {
            throw new CompanyException("The company's history doesn't exist");
        }
    }

    @Override
    @Transactional
    public List<Address> findAddress(Long id, Integer offset, Integer pageSize) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        if (company != null && company.getAddress() != null) {
            Page<AddressEntity> list = companyRepository.findCompanyAddress(id, PageRequest.of(offset, pageSize));
            return list.stream().map(addressOutputMapper::toAddress).collect(Collectors.toList());
        } else {
            throw new CompanyException("The list of company's address is null");
        }
    }

    @Override
    public Company findByEmail(String email) {
        CompanyEntity company = companyRepository.findCompanyByEmail(email).orElse(null);
        if (company != null) {
            return companyOutputMapper.toCompany(company);
        } else {
            throw new CompanyException("The company fetched by email doesn't exist");
        }
    }

    @Override
    public Company changePwd(String pwd, Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            company.setPwd(passwordEncoder.encode(pwd));
            return companyOutputMapper.toCompany(company);
        } else {
            throw new CompanyException("The company fetched to change its pwd doesn't exist");
        }
    }

    @Override
    @Transactional
    public List<Address> addAddress(Address address, Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        if (company != null && address != null) {
            if (address.getId() != null) {
                //the address exist
                AddressEntity addressFound = addressRepository.findById(address.getId()).orElse(null);
                if (addressFound != null) {
                    company.getAddress().add(addressFound);
                } else {
                    throw new CompanyException("The address to add doesn't exist");
                }
            } else {
                //the address doesn't exist
                AddressEntity a = new AddressEntity(null, address.getStreet(), address.getCountry(), address.getPostalCode());
                company.getAddress().add(a);
            }
            companyRepository.save(company);
            Page<AddressEntity> list = companyRepository.findCompanyAddress(id, PageRequest.of(0, 10));
            return list.getContent().stream()
                    .map(addressOutputMapper::toAddress)
                    .collect(Collectors.toList());
        } else {
            throw new CompanyException("The company or the address to add doesn't exist");
        }
    }

    @Override
    @Transactional
    public boolean removeAddress(Long addressId, Long companyId) {
        AddressEntity address = addressRepository.findById(addressId).orElse(null);
        CompanyEntity company = companyRepository.findById(companyId).orElse(null);
        if (company != null && address != null) {
            company.getAddress().remove(address);
            companyRepository.save(company);
            return true;
        } else {
            throw new CompanyException("The company or the address to remove doesn't exist");
        }
    }

}
