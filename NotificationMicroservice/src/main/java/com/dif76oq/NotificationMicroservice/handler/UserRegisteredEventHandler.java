package com.dif76oq.NotificationMicroservice.handler;

import com.dif76oq.NotificationMicroservice.service.EmailService;
import com.dif76oq.NotificationMicroservice.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRegisteredEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final EmailServiceImpl emailService;

    @KafkaListener(topics = "user-registered-events-topic")
    public void handle(Integer userId) {
        LOGGER.info("Received event: {}", userId);
        try {
            emailService.sendVerificationEmail(userId);
            LOGGER.info("Verification message sent successfully");
        }
        catch (MessagingException e) {
            LOGGER.error("An error occurred while sending verification message {}", e.getMessage());
        }
    }
}
