package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Company;

import java.util.List;

public interface CompanyInputPort {
    Company findById(Long id);

    List<Company> findAll();

    Company save(Company company);

    Company update(Long id,Company company);

    void deleteById(Long id);

}
