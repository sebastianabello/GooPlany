package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.ConfirmationTokenOutputPort;
import com.gooplanycol.gooplany.domain.model.ConfirmationToken;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ConfirmationTokenOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ConfirmationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConfirmationTokenOutputAdapter implements ConfirmationTokenOutputPort {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenOutputMapper mapper;

    @Override
    @Transactional
    public void saveConfirmationToken(ConfirmationToken token) {
        mapper.toConfirmationToken(mapper.toConfirmationTokenEntity((token)));
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return Optional.ofNullable(mapper.toConfirmationToken(confirmationTokenRepository.findByToken(token).orElse(null)));
    }

    public void setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = mapper.toConfirmationToken(confirmationTokenRepository.findByToken(token).orElse(null));
        if (confirmationToken == null) {
            throw new IllegalStateException("The token doesn't exist");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }

}
