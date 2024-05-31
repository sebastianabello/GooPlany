package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.ConfirmationTokenInputPort;
import com.gooplanycol.gooplany.application.ports.output.ConfirmationTokenOutputPort;
import com.gooplanycol.gooplany.domain.model.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService implements ConfirmationTokenInputPort {

    private final ConfirmationTokenOutputPort confirmationTokenOutputPort;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenOutputPort.saveConfirmationToken(token);
    }
}
