package com.gooplanycol.gooplany.application.ports.output;


import com.gooplanycol.gooplany.domain.model.ConfirmationToken;

public interface ConfirmationTokenOutputPort {
    void saveConfirmationToken(ConfirmationToken token);
}
