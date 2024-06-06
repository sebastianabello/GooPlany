package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.output.EmailInputPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailInputPort {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailUser;

    @Async
    @Override
    public void send(String to, String email) {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage,"utf-8");
            helper.setFrom(emailUser);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setText(email,true);
            javaMailSender.send(mimeMailMessage);
        }catch (MessagingException e){
            throw new IllegalStateException("Failed to send the email");
        }
    }
}
