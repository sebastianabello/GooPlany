package com.gooplanycol.gooplany.application.ports.output;


import com.gooplanycol.gooplany.domain.model.ConfirmationTokenCustomer;

import java.util.Optional;

public interface ConfirmationTokenCustomerOutputPort {
    void saveConfirmationToken(ConfirmationTokenCustomer token);

    Optional<ConfirmationTokenCustomer> getToken(String token);

    void setConfirmedAt(String token);
}
