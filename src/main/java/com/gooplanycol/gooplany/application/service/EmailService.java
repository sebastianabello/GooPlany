package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.EmailInputPort;
import com.gooplanycol.gooplany.application.ports.input.ProfileInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailInputPort {

    private final JavaMailSender mailSender;
    private final ProfileInputPort profileInputPort;

    @Value("${spring.mail.username}")
    private String emailUser;

    public void sendEmailToProfiles(Long eventId, String subject, String text) {
        List<String> emails = profileInputPort.getEmailsByEventId(eventId);

        for (String email : emails) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailUser);
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);
            mailSender.send(mailMessage);
        }
    }


}
