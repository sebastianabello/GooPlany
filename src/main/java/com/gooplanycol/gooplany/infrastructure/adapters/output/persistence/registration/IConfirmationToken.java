package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.registration;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationToken;

public interface IConfirmationToken {
    void saveConfirmationToken(ConfirmationToken token);
}
