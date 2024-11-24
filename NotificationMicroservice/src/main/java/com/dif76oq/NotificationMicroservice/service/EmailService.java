package com.dif76oq.NotificationMicroservice.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendVerificationEmail(Integer userId) throws MessagingException;
}
