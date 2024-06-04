package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.ConfirmationTokenCompanyOutputPort;
import com.gooplanycol.gooplany.application.ports.output.ConfirmationTokenCustomerOutputPort;
import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCompany;
import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCustomer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCompanyEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ConfirmationTokenCompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ConfirmationTokenCustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ConfirmationTokenCompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ConfirmationTokenCustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ConfirmationTokenCompanyOutputAdapter implements ConfirmationTokenCompanyOutputPort {

    private final ConfirmationTokenCompanyRepository confirmationTokenRepository;
    private final ConfirmationTokenCompanyOutputMapper mapper;


    @Override
    public void saveConfirmationToken(ConfirmationTokenCompany token) {
    ConfirmationTokenCompanyEntity c = confirmationTokenRepository.save(mapper.toConfirmationTokenCompanyEntity(token));
    }

    @Override
    public Optional<ConfirmationTokenCompany> getToken(String token) {
        return confirmationTokenRepository.findByToken(token).map(mapper::toConfirmationTokenCompany);
    }

    @Override
    public void setConfirmedAt(String token) {
        ConfirmationTokenCompanyEntity confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null);
        if (confirmationToken == null) {
            throw new IllegalStateException("The token doesn't exist");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }
}
