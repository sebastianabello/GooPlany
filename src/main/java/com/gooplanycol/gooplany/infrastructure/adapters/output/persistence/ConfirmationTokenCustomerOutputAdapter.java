package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.ConfirmationTokenCustomerOutputPort;
import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCustomer;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationTokenCustomerEntity;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ConfirmationTokenCustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ConfirmationTokenCustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ConfirmationTokenCustomerOutputAdapter implements ConfirmationTokenCustomerOutputPort {

    private final ConfirmationTokenCustomerRepository confirmationTokenRepository;
    private final ConfirmationTokenCustomerOutputMapper mapper;

    @Override
    @Transactional
    public void saveConfirmationToken(ConfirmationTokenCustomer token) {
        ConfirmationTokenCustomerEntity c = confirmationTokenRepository.save(mapper.toConfirmationTokenCustomerEntity(token));
    }

    @Override
    public Optional<ConfirmationTokenCustomer> getToken(String token) {
        return confirmationTokenRepository.findByToken(token).map(mapper::toConfirmationTokenCustomer);

    }

    @Override
    public void setConfirmedAt(String token) {
        ConfirmationTokenCustomerEntity confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null);
        if (confirmationToken == null) {
            throw new IllegalStateException("The token doesn't exist");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }
}
