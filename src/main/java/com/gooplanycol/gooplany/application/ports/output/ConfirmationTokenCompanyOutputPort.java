package com.gooplanycol.gooplany.application.ports.output;


import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCompany;

import java.util.Optional;

public interface ConfirmationTokenCompanyOutputPort {
    void saveConfirmationToken(ConfirmationTokenCompany token);

    Optional<ConfirmationTokenCompany> getToken(String token);

    void setConfirmedAt(String token);
}
