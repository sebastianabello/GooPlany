package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyOutPort {
    Optional<Company> findById(Long id);

    List<Company> findAll();

    Company save(Company company);

    void deleteById(Long id);
}
