package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.registration;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationToken;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenOutputAdapter implements IConfirmationToken {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        ConfirmationToken c = confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null);
        if (confirmationToken == null) {
            throw new IllegalStateException("The token doesn't exist");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }
}
