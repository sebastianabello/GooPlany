package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.CompanyOutPort;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyOutputAdapter implements CompanyOutPort {
    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper mapper;

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id)
                .map(mapper::toCompany);
    }

    @Override
    public List<Company> findAll() {
        return mapper.toCompanyList(companyRepository.findAll());
    }

    @Override
    public Company save(Company company) {
        return mapper.toCompany(companyRepository.save(mapper.toCompanyEntity(company)));
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
