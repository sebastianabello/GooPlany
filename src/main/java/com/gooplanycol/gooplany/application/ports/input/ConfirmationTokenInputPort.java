package com.gooplanycol.gooplany.application.ports.input;


import com.gooplanycol.gooplany.domain.model.ConfirmationToken;

public interface ConfirmationTokenInputPort {
    void saveConfirmationToken(ConfirmationToken token);
}
