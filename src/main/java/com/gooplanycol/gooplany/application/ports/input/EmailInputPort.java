package com.gooplanycol.gooplany.application.ports.input;


public interface EmailInputPort {

    void send(String to, String email);

    // void sendEmailToProfiles(Long eventId, String subject, String text);
}
