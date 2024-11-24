package com.dif76oq.NotificationMicroservice.service.impl;

import com.dif76oq.NotificationMicroservice.model.User;
import com.dif76oq.NotificationMicroservice.repository.UserRepository;
import com.dif76oq.NotificationMicroservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender emailSender;
    private UserRepository userRepository;


//    public void resendVerificationCode(){
//
//    }

    public void sendVerificationEmail(Integer userId) throws MessagingException {

        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setFrom("xnikitopolisx@gmail.com");
            helper.setTo(user.get().getEmail());
            helper.setSubject("Account verification");
            String text = "<html>"
                    + "<body style=\"font-family: Arial, sans-serif;\">"
                    + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                    + "<h2 style=\"color: #333;\">Hello " + user.get().getUsername() + "! Welcome to our app!</h2>"
                    + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                    + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                    + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                    + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + user.get().getVerificationCode() + "</p>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
            helper.setText(text, true);


            emailSender.send(message);
        }
    }
}
