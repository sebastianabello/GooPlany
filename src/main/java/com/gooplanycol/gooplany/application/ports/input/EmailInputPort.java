package com.gooplanycol.gooplany.application.ports.input;


public interface EmailInputPort {
    void sendEmailToProfiles(Long eventId, String subject, String text);
}
