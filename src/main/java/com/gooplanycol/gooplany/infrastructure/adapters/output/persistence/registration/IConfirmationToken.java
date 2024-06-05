package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.registration;

import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.ConfirmationToken;

public interface IConfirmationToken {
    public void saveConfirmationToken(ConfirmationToken token);
}
