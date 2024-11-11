package com.dif76oq.userMicroservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl {
    private JavaMailSender emailSender;

    public void sendVerificationEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        message.setFrom("xnikitopolisx@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);


        emailSender.send(message);
    }
}
